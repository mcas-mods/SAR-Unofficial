package xyz.brassgoggledcoders.steamagerevolution.utils.recipe;

import java.util.*;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreIngredient;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;

public class RecipeMachineHelper {
	public static void onFinish(SARMachineRecipe currentRecipe, InventoryMachine inventory) {
		boolean extractedItems = true;
		boolean extractedFluids = true;
		boolean extractedSteam = true;
		if(ArrayUtils.isNotEmpty(currentRecipe.getItemInputs())) {
			for(Ingredient input : currentRecipe.getItemInputs()) {
				// TODO
				if(input instanceof OreIngredient) {
					OreIngredient oreIng = (OreIngredient) input;
					for(int i = 0; i < inventory.getInputHandler().getSlots(); i++) {
						if(oreIng.apply(inventory.getInputHandler().getStackInSlot(i))) {
							inventory.getInputHandler().extractItem(i, 1/* TODO */, false);
							break;
						}
					}
				}
				else {
					for(ItemStack stack : input.getMatchingStacks()) {
						extractedItems = inventory.getInputHandler().extractStack(stack);
					}
				}
			}
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getFluidInputs())) {
			for(IngredientFluidStack input : currentRecipe.getFluidInputs()) {
				if(inventory.getInputTank().drain(input.getFluid(), false) != null
						&& inventory.getInputTank().drain(input.getFluid(), false).amount == input.getFluid().amount) {
					inventory.getInputTank().drain(input.getFluid(), true);
				}
				else {
					extractedFluids = false;
				}
			}
		}
		if(inventory.steamTank != null) {
			if(inventory.steamTank.getHandler().getFluidAmount() >= currentRecipe.getSteamUsePerCraft()) {
				inventory.steamTank.getHandler().drain(currentRecipe.getSteamUsePerCraft(), true);
			}
			else {
				extractedSteam = false;
			}
		}
		if(extractedItems && extractedFluids && extractedSteam) {
			if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
				for(ItemStack output : currentRecipe.getItemOutputs()) {
					ItemHandlerHelper.insertItem(inventory.getOutputHandler(), output, false);
				}
			}
			if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
				for(FluidStack output : currentRecipe.getFluidOutputs()) {
					inventory.getOutputTank().fill(output, true);
				}
			}
		}
		else {
			SteamAgeRevolution.instance.getLogger()
					.info("Machine encountered recipe error at final stage. This should not happen..." + extractedItems
							+ "/" + extractedFluids + "/" + extractedSteam);
		}
	}

	public static boolean canFinish(int currentTicks, SARMachineRecipe currentRecipe, InventoryMachine inventory) {
		if(currentRecipe != null && currentTicks >= currentRecipe.getTicks()) {
			boolean roomForItems = true;
			boolean roomForFluids = true;
			if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
				roomForItems = Arrays.asList(currentRecipe.getItemOutputs()).parallelStream().allMatch(
						output -> ItemHandlerHelper.insertItem(inventory.getOutputHandler(), output, true).isEmpty());
			}
			if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
				roomForFluids = Arrays.asList(currentRecipe.getFluidOutputs()).parallelStream()
						.allMatch(output -> inventory.getOutputTank().fill(output, false) == output.amount);
			}
			return roomForItems && roomForFluids;
		}
		return false;
	}

	public static boolean canRun(World world, BlockPos pos, IHasInventory handler, String name,
			SARMachineRecipe currentRecipe, InventoryMachine inventory) {
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

	private static boolean hasRequiredFluids(InventoryMachine inventory, SARMachineRecipe recipe) {
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

	private static boolean tanksHaveFluid(InventoryMachine inventory, IngredientFluidStack stack) {
		return Arrays
				.asList(inventory.getInputTank()).stream().filter(Objects::nonNull).filter(tank -> tank.fluids.stream()
						.filter(Objects::nonNull).anyMatch(fluid -> fluid.containsFluid(stack.getFluid())))
				.findAny().isPresent();
	}

	private static boolean hasRequiredItems(InventoryMachine inventory, SARMachineRecipe recipe) {
		if(ArrayUtils.isNotEmpty(recipe.getItemInputs())) {
			return Arrays.stream(recipe.getItemInputs()).map(ing -> handlerHasItems(inventory, ing))
					.reduce((a, b) -> a && b).orElse(false);
		}
		return true;
	}

	private static boolean handlerHasItems(InventoryMachine inventory, Ingredient ingredient) {
		return IntStream.range(0, inventory.getInputHandler().getSlots())
				.mapToObj(slotNum -> inventory.getInputHandler().getStackInSlot(slotNum))
				.filter(inputStack -> Arrays.asList(ingredient.getMatchingStacks()).stream()
						.anyMatch(stack -> ItemStackUtils.containsItemStack(inputStack, stack)))
				.findAny().isPresent();
	}
}
