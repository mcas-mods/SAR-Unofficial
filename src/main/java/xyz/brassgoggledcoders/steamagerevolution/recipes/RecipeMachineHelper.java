package xyz.brassgoggledcoders.steamagerevolution.recipes;

import java.util.*;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.teamacronymcoders.base.util.inventory.IngredientFluidStack;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;

public class RecipeMachineHelper {
	public static void onFinish(SARMachineRecipe currentRecipe, InventoryBasic inventory) {
		boolean extractedItems = true;
		boolean extractedFluids = true;
		boolean extractedSteam = true;
		if(ArrayUtils.isNotEmpty(currentRecipe.getItemInputs())) {
			// TODO Shouldn't be looping twice here
			int matched = 0;
			for(Ingredient input : currentRecipe.getItemInputs()) {
				for(int i = 0; i < inventory.getInputItemHandler().getSlots(); i++) {
					if(input.apply(inventory.getInputItemHandler().getStackInSlot(i))) {
						inventory.getInputItemHandler().extractItem(i, 1, false);
						matched++;
					}
				}
			}
			extractedItems = (currentRecipe.getItemInputs().length == matched);
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getFluidInputs())) {
			for(IngredientFluidStack input : currentRecipe.getFluidInputs()) {
				if(inventory.getInputFluidHandler().drain(input.getFluid(), false) != null
						&& inventory.getInputFluidHandler().drain(input.getFluid(), false).amount == input.getFluid().amount) {
					inventory.getInputFluidHandler().drain(input.getFluid(), true);
				}
				else {
					extractedFluids = false;
				}
			}
		}
		if(inventory.steamTankPiece != null) {
			if(inventory.steamTankPiece.getHandler().getFluidAmount() >= currentRecipe.getSteamUsePerCraft()) {
				inventory.steamTankPiece.getHandler().drain(currentRecipe.getSteamUsePerCraft(), true);
			}
			else {
				extractedSteam = false;
			}
		}
		if(extractedItems && extractedFluids && extractedSteam) {
			if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
				for(ItemStack output : currentRecipe.getItemOutputs().clone()) {
					ItemHandlerHelper.insertItem(inventory.getOutputItemHandler(), output.copy(), false);
				}
			}
			if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
				for(FluidStack output : currentRecipe.getFluidOutputs().clone()) {
					inventory.getOutputFluidHandler().fill(output.copy(), true);
				}
			}
		}
		else {
			SteamAgeRevolution.instance.getLogger()
					.info("Machine encountered recipe error at final stage. This should not happen..." + extractedItems
							+ "/" + extractedFluids + "/" + extractedSteam);
		}
	}

	public static boolean canFinish(int currentTicks, SARMachineRecipe currentRecipe,
			InventoryBasic inventory) {
		if(currentRecipe != null && currentTicks >= currentRecipe.getTicksPerOperation()) {
			boolean roomForItems = true;
			boolean roomForFluids = true;
			if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
				roomForItems = Arrays.asList(currentRecipe.getItemOutputs()).parallelStream().allMatch(
						output -> ItemHandlerHelper.insertItem(inventory.getOutputItemHandler(), output, true).isEmpty());
			}
			if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
				roomForFluids = Arrays.asList(currentRecipe.getFluidOutputs()).parallelStream()
						.allMatch(output -> inventory.getOutputFluidHandler().fill(output, false) == output.amount);
			}
			return roomForItems && roomForFluids;
		}
		return false;
	}

	public static boolean canRun(World world, BlockPos pos, IMachineHasInventory handler, String name,
			SARMachineRecipe currentRecipe, InventoryBasic inventory) {
		if(currentRecipe != null) {
			if(inventory.getSteamTank() == null
					|| inventory.getSteamTank().getFluidAmount() >= currentRecipe.getSteamUsePerCraft()) {
				return true;
			}
		}
		else {
			Optional<SARMachineRecipe> recipe = RecipeRegistry.getRecipesForMachine(name).parallelStream()
					.filter(r -> hasRequiredFluids(inventory, r)).filter(r -> hasRequiredItems(inventory, r))
					.findFirst();
			if(recipe.isPresent()) {
				currentRecipe = recipe.get();
				handler.setCurrentRecipe(currentRecipe);
			}
		}
		return false;
	}

	public static boolean hasRequiredFluids(InventoryBasic inventory, SARMachineRecipe recipe) {
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

	private static boolean tanksHaveFluid(InventoryBasic inventory, IngredientFluidStack stack) {
		return Arrays
				.asList(inventory.getInputFluidHandler()).stream().filter(Objects::nonNull).filter(tank -> tank.fluids.stream()
						.filter(Objects::nonNull).anyMatch(fluid -> fluid.containsFluid(stack.getFluid())))
				.findAny().isPresent();
	}

	public static boolean hasRequiredItems(InventoryBasic inventory, SARMachineRecipe recipe) {
		if(ArrayUtils.isNotEmpty(recipe.getItemInputs())) {
			return Arrays.stream(recipe.getItemInputs()).map(ing -> handlerHasItems(inventory, ing))
					.reduce((a, b) -> a && b).orElse(false);
		}
		return true;
	}

	private static boolean handlerHasItems(InventoryBasic inventory, Ingredient ingredient) {
		return IntStream.range(0, inventory.getInputItemHandler().getSlots())
				.mapToObj(slotNum -> inventory.getInputItemHandler().getStackInSlot(slotNum))
				.filter(inputStack -> ingredient.apply(inputStack)).findAny().isPresent();
	}
}
