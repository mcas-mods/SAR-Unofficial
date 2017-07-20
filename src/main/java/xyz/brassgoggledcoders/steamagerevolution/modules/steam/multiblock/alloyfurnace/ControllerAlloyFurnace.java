package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityCastingBench;

public class ControllerAlloyFurnace extends RectangularMultiblockControllerBase implements ISmartTankCallback {

	public FluidTank steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this);
	public FluidTank inputTank1 = new FluidTankSmart(TileEntityCastingBench.VALUE_BLOCK * 16, this);
	public FluidTank inputTank2 = new FluidTankSmart(TileEntityCastingBench.VALUE_BLOCK * 16, this);
	public ItemStackHandler inputSolid = new ItemStackHandler();
	public FluidTank outputTank = new FluidTank(Fluid.BUCKET_VOLUME * 16);

	public boolean isHardened = true;
	// public int temperature = 0;

	public static final int steamUsePerHeat = Fluid.BUCKET_VOLUME / 10;
	public static final int steamUsePerMaintain = Fluid.BUCKET_VOLUME / 100;
	// public static final int maxTemperature = 1000;

	public ControllerAlloyFurnace(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;

		boolean hasFirstFluid = inputTank1.getFluid() != null;
		boolean hasSecondFluid = inputTank2.getFluid() != null;
		boolean hasItems = !inputSolid.getStackInSlot(0).isEmpty();

		// Can't do anything without a base for the alloy
		if(hasFirstFluid) {
			if(hasSecondFluid) {
				AlloyFurnaceRecipe r =
						AlloyFurnaceRecipe.getRecipe(Pair.of(inputTank1.getFluid(), inputTank2.getFluid()));
				if(r != null && (!r.requiresHardCase || isHardened)) {
					if(inputTank1.drain(r.input.getLeft(), false) != null
							&& inputTank2.drain((FluidStack) r.input.getRight(), false) != null
							&& outputTank.fill(r.output, false) == r.output.amount) {
						inputTank1.drain(r.input.getLeft(), true);
						inputTank2.drain((FluidStack) r.input.getRight(), true);
						outputTank.fill(r.output, true);
					}
				}
			}
			if(hasItems) {
				AlloyFurnaceRecipe r =
						AlloyFurnaceRecipe.getRecipe(Pair.of(inputTank1.getFluid(), inputSolid.getStackInSlot(0)));
				FMLLog.warning(String.valueOf(r != null));
				if(r != null && (!r.requiresHardCase || isHardened)) {
					int count = ((ItemStack) r.input.getRight()).getCount();
					if(inputTank1.drain(r.input.getLeft(), false) != null
							&& inputSolid.getStackInSlot(0).getCount() >= count
							&& outputTank.fill(r.output, false) == r.output.amount) {
						inputTank1.drain(r.input.getLeft(), true);
						inputSolid.extractItem(0, count, false);
						outputTank.fill(r.output, true);
					}
				}
			}
			// else do nothing, can't make a recipe with one fluid
		}

		return flag;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO
		return 1;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.isAirBlock(new BlockPos(x, y, z)))
			return true;
		else
			return false;
	}

	// May allow larger smelteries in future.

	@Override
	protected int getMinimumXSize() {
		return 3;
	}

	@Override
	protected int getMinimumZSize() {
		return 3;
	}

	@Override
	protected int getMinimumYSize() {
		return 3;
	}

	@Override
	protected int getMaximumXSize() {
		return 5;
	}

	@Override
	protected int getMaximumZSize() {
		return 5;
	}

	@Override
	protected int getMaximumYSize() {
		return 9;
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineAssembled() {

	}

	@Override
	protected void onMachineRestored() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachinePaused() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineDisassembled() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateClient() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		isHardened = data.getBoolean("hardened");
		inputSolid.deserializeNBT(data.getCompoundTag("solidInput"));
		inputTank1.readFromNBT(data.getCompoundTag("fluidInput1"));
		inputTank2.readFromNBT(data.getCompoundTag("fluidInput2"));
		outputTank.readFromNBT(data.getCompoundTag("output"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setBoolean("hardened", isHardened);
		data.setTag("solidInput", inputSolid.serializeNBT());
		data.setTag("fluidInput1", inputTank1.writeToNBT(new NBTTagCompound()));
		data.setTag("fluidInput2", inputTank2.writeToNBT(new NBTTagCompound()));
		data.setTag("output", outputTank.writeToNBT(new NBTTagCompound()));
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

}
