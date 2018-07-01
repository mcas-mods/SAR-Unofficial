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
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketRecipeUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;

public class RecipeMachineHelper {
	public static void onFinish(SARMachineRecipe currentRecipe, InventoryMachine inventory) {
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
		if(ArrayUtils.isNotEmpty(currentRecipe.getItemInputs())) {
			for(Ingredient input : currentRecipe.getItemInputs()) {
				// TODO Inefficient for oredict
				for(ItemStack stack : input.getMatchingStacks()) {
					inventory.getInputHandler().extractStack(stack);
				}
			}
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getFluidInputs())) {
			for(IngredientFluidStack input : currentRecipe.getFluidInputs()) {
				inventory.getInputTank().drain(input.getFluid(), true);
			}
		}
		if(inventory.steamTank != null) {
			inventory.steamTank.getHandler().drain(currentRecipe.getSteamUsePerCraft(), true);
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
		Optional<SARMachineRecipe> recipe = RecipeRegistry.getRecipesForMachine(name).parallelStream()
				.filter(r -> hasRequiredFluids(inventory, r)).filter(r -> hasRequiredItems(inventory, r)).findFirst();
		if(recipe.isPresent()) {
			currentRecipe = recipe.get();
			handler.setCurrentRecipe(currentRecipe);
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketRecipeUpdate(currentRecipe.networkID, pos, name), pos, world.provider.getDimension());
			// SteamAgeRevolution.instance.getLogger().devInfo("Found recipe for " + name);
		}
		if(currentRecipe == null) {
			return false;
		}
		else if(inventory.getSteamTank() == null
				|| inventory.getSteamTank().getFluidAmount() >= currentRecipe.getSteamUsePerCraft()) {

			return true;
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
						.anyMatch(stack -> ItemStackUtils.containsItemStack(stack, inputStack)))
				.findAny().isPresent();
	}
}
