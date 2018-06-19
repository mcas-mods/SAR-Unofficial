package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.*;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.utils.RecipeRegistry;
import xyz.brassgoggledcoders.steamagerevolution.utils.SARMachineRecipe;

public abstract class MultiblockLogicFramework extends RectangularMultiblockControllerBase
		implements ISARMachine, IMultiblockControllerInfo {

	protected int ticksPerCycle = 20;
	protected int currentTicks = 0;
	SARMachineRecipe currentRecipe = null;

	protected MultiblockLogicFramework(World world) {
		super(world);
	}

	protected MultiblockLogicFramework(World world, int ticksPerCycle) {
		this(world);
		this.ticksPerCycle = ticksPerCycle;
	}

	@Override
	protected boolean updateServer() {
		if(canRun()) {
			onActiveTick();
			currentTicks++;
			if(canFinish()) {
				onFinish();
			}
			return true; // TODO
		}
		return false;
	}

	protected void onActiveTick() {
		// TODO Auto-generated method stub

	}

	protected void onFinish() {
		if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
			for(ItemStack output : currentRecipe.getItemOutputs()) {
				ItemHandlerHelper.insertItem(getItemOutput(), output, false);
			}
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
			for(FluidStack output : currentRecipe.getFluidOutputs()) {
				this.getFluidOutputs().fill(output, true);
			}
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getItemInputs())) {
			for(ItemStack input : currentRecipe.getItemInputs()) {
				this.getItemOutput().extractStack(input);
			}
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getFluidInputs())) {
			for(FluidStack input : currentRecipe.getFluidInputs()) {
				this.getFluidInputs().drain(input, true);
			}
		}
		currentTicks = 0;
	}

	protected boolean canFinish() {
		if(currentTicks >= ticksPerCycle) {
			boolean roomForItems = true;
			boolean roomForFluids = true;
			if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
				roomForItems = Arrays.asList(currentRecipe.getItemOutputs()).parallelStream()
						.allMatch(output -> ItemHandlerHelper.insertItem(getItemOutput(), output, true).isEmpty());
			}
			if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
				roomForFluids = Arrays.asList(currentRecipe.getFluidOutputs()).parallelStream()
						.allMatch(output -> this.getFluidOutputs().fill(output, false) == output.amount);
			}
			return roomForItems && roomForFluids;
		}
		return false;
	}

	protected boolean canRun() {
		Optional<SARMachineRecipe> recipe = RecipeRegistry.getRecipesForMachine(this.getName().toLowerCase())
				.parallelStream().filter(this::hasRequiredFluids).filter(this::hasRequiredItems).findFirst();
		if(recipe.isPresent()) {
			currentRecipe = recipe.get();
			return true;
		}
		return currentRecipe != null;
	}

	private boolean hasRequiredFluids(SARMachineRecipe recipe) {
		if(ArrayUtils.isNotEmpty(recipe.getFluidInputs())) {
			// Stream the fluid stacks
			return Arrays.stream(recipe.getFluidInputs())
					// Apply tanksHaveFluid to each element and output result to stream
					.map(this::tanksHaveFluid)
					// Reduce list of booleans into one - so will only evaluate true if every boolean is true
					.reduce((a, b) -> a && b).orElse(false);
		}
		return true;
	}

	private boolean tanksHaveFluid(FluidStack stack) {
		return Arrays.asList(this.getFluidInputs()).stream().filter(Objects::nonNull).filter(
				tank -> tank.fluids.stream().filter(Objects::nonNull).anyMatch(fluid -> fluid.containsFluid(stack)))
				.findAny().isPresent();
	}

	private boolean hasRequiredItems(SARMachineRecipe recipe) {
		if(ArrayUtils.isNotEmpty(recipe.getItemInputs())) {
			return Arrays.stream(recipe.getItemInputs()).map(this::handlerHasItems).reduce((a, b) -> a && b)
					.orElse(false);
		}
		return true;
	}

	private boolean handlerHasItems(ItemStack stack) {
		return IntStream.range(0, this.getItemInput().getSlots())
				.mapToObj(slotNum -> this.getItemInput().getStackInSlot(slotNum))
				.filter(inputStack -> ItemStackUtils.containsItemStack(stack, inputStack)).findAny().isPresent();
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		currentTicks = data.getInteger("progress");
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setInteger("progress", currentTicks);
	}

	// Modify from protected to public
	@Override
	public int getMinimumXSize() {
		return 0;
	}

	@Override
	public int getMinimumYSize() {
		return 0;
	}

	@Override
	public int getMinimumZSize() {
		return 0;
	}

}
