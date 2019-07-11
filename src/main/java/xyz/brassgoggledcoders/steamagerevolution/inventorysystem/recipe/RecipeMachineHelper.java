package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import java.util.*;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.teamacronymcoders.base.util.inventory.IngredientFluidStack;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;

public class RecipeMachineHelper {
	public static void onFinish(SARMachineRecipe currentRecipe, InventoryRecipe inventory) {
		boolean extractedItems = true;
		boolean extractedFluids = true;
		boolean extractedSteam = true;
		if(ArrayUtils.isNotEmpty(currentRecipe.getItemInputs())) {
			// TODO Good lord loops
			int matched = 0;
			for(Ingredient input : currentRecipe.getItemInputs()) {
				for(ItemStackHandler handler : inventory.getTypedItemHandlers(IOType.INPUT)) {
					for(int i = 0; i < handler.getSlots(); i++) {
						if(input.apply(handler.getStackInSlot(i))) {
							handler.extractItem(i, 1, false);
							matched++;
						}
					}
				}
			}
			extractedItems = (currentRecipe.getItemInputs().length == matched);
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getFluidInputs())) {
			for(IngredientFluidStack input : currentRecipe.getFluidInputs()) {
				for(FluidTank tank : inventory.getTypedFluidHandlers(IOType.INPUT)) {
					if(tank.drain(input.getFluid(), false) != null
							&& tank.drain(input.getFluid(), false).amount == input.getFluid().amount) {
						tank.drain(input.getFluid(), true);
					}
					else {
						extractedFluids = false;
					}
				}
			}
		}
		if(inventory.steamPiece != null) {
			if(inventory.steamPiece.getHandler().getFluidAmount() >= currentRecipe.getSteamUsePerCraft()) {
				inventory.steamPiece.getHandler().drain(currentRecipe.getSteamUsePerCraft(), true);
			}
			else {
				extractedSteam = false;
			}
		}
		if(extractedItems && extractedFluids && extractedSteam) {
			if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
				for(ItemStack output : currentRecipe.getItemOutputs().clone()) {
					for(ItemStackHandler handler : inventory.getTypedItemHandlers(IOType.OUTPUT)) {
						ItemHandlerHelper.insertItem(handler, output.copy(), false);
					}
				}
			}
			if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
				for(FluidStack output : currentRecipe.getFluidOutputs().clone()) {
					for(FluidTank tank : inventory.getTypedFluidHandlers(IOType.INPUT)) {
						tank.fill(output.copy(), true);
					}
				}
			}
		}
		else {
			SteamAgeRevolution.instance.getLogger()
					.info("Machine encountered recipe error at final stage. This should not happen..." + extractedItems
							+ "/" + extractedFluids + "/" + extractedSteam);
		}
	}

	public static boolean canFinish(int currentTicks, SARMachineRecipe currentRecipe, InventoryRecipe inventory) {
		if(currentRecipe != null && currentTicks >= currentRecipe.getTicksPerOperation()) {
			boolean roomForItems = true;
			boolean roomForFluids = true;
			if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
				roomForItems = Arrays.asList(currentRecipe.getItemOutputs()).parallelStream()
						.allMatch(output -> inventory.getTypedItemHandlers(IOType.OUTPUT).stream()
								.anyMatch(h -> ItemHandlerHelper.insertItem(h, output, true).isEmpty()));
			}
			if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
				roomForFluids = Arrays.asList(currentRecipe.getFluidOutputs()).parallelStream()
						.allMatch(output -> inventory.getTypedFluidHandlers(IOType.OUTPUT).stream()
								.anyMatch(t -> t.fill(output, false) == output.amount));
			}
			return roomForItems && roomForFluids;
		}
		return false;
	}

	public static boolean canRun(World world, BlockPos pos, IHasInventory<? extends InventoryRecipe> handler,
			String name, SARMachineRecipe currentRecipe, InventoryRecipe inventory) {
		if(currentRecipe != null) {
			if(inventory.steamPiece.getHandler() == null
					|| inventory.steamPiece.getHandler().getFluidAmount() >= currentRecipe.getSteamUsePerCraft()) {
				return true;
			}
		}
		else {
			Optional<SARMachineRecipe> recipe = RecipeRegistry.getRecipesForMachine(name).parallelStream()
					.filter(r -> hasRequiredFluids(inventory, r)).filter(r -> hasRequiredItems(inventory, r))
					.findFirst();
			if(recipe.isPresent()) {
				currentRecipe = recipe.get();
				handler.getInventory().setCurrentRecipe(currentRecipe);
			}
		}
		return false;
	}

	public static boolean hasRequiredFluids(InventoryRecipe inventory, SARMachineRecipe recipe) {
		if(ArrayUtils.isNotEmpty(recipe.getFluidInputs())) {
			// Stream the fluid stacks
			return Arrays.stream(recipe.getFluidInputs())
					// Apply tanksHaveFluid to each element and output result to stream
					.map(stack -> tanksHaveFluid(inventory, stack))
					// Reduce list of booleans into one - so will only evaluate true if every
					// boolean is true
					.reduce((a, b) -> a && b).orElse(false);
		}
		return true;
	}

	private static boolean tanksHaveFluid(InventoryRecipe inventory, IngredientFluidStack stack) {
		return inventory.getTypedFluidHandlers(IOType.INPUT).stream().filter(Objects::nonNull)
				.filter(tank -> tank.getFluid().containsFluid(stack.getFluid())).findAny().isPresent();
	}

	public static boolean hasRequiredItems(InventoryRecipe inventory, SARMachineRecipe recipe) {
		if(ArrayUtils.isNotEmpty(recipe.getItemInputs())) {
			return Arrays.stream(recipe.getItemInputs()).map(ing -> handlerHasItems(inventory, ing))
					.reduce((a, b) -> a && b).orElse(false);
		}
		return true;
	}

	private static boolean handlerHasItems(InventoryRecipe inventory, Ingredient ingredient) {
		return inventory.getTypedItemHandlers(IOType.INPUT).stream()
				.filter(handler -> IntStream.range(0, handler.getSlots())
						.mapToObj(slotNum -> handler.getStackInSlot(slotNum))
						.filter(inputStack -> ingredient.apply(inputStack)).findAny().isPresent())
				.findAny().isPresent();
	}
}
