package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.SARRectangularMultiblockControllerBase;

public class ControllerAlloyFurnace extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	public FluidTank primaryTank = new FluidTankSmart(TileEntityCastingBench.VALUE_BLOCK * 16, this);
	public FluidTank secondaryTank = new FluidTankSmart(TileEntityCastingBench.VALUE_BLOCK * 16, this);
	public FluidTank outputTank = new FluidTank(Fluid.BUCKET_VOLUME * 16);

	public ControllerAlloyFurnace(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;

		boolean hasFirstFluid = primaryTank.getFluid() != null;
		boolean hasSecondFluid = secondaryTank.getFluid() != null;

		// Can't do anything without a base for the alloy
		if(hasFirstFluid && hasSecondFluid) {
			AlloyFurnaceRecipe r = AlloyFurnaceRecipe.getRecipe(primaryTank.getFluid(), secondaryTank.getFluid());
			if(r != null) {
				if(primaryTank.getFluidAmount() >= r.primaryInput.amount) {
					if(secondaryTank.getFluidAmount() >= r.secondaryInputFluid.amount) {
						if(outputTank.fill(r.output, false) == r.output.amount) {
							primaryTank.drain(r.primaryInput.amount, true);
							secondaryTank.drain(r.secondaryInputFluid.amount, true);
							outputTank.fill(r.output, true);
							flag = true;
						}
					}
				}
			}
		}
		// else do nothing, can't make a recipe with one fluid
		return flag;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO
		return 1;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		if(block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
			return true;
		}
		else {
			validatorCallback
					.setLastError(new ValidationError("steamagerevolution.multiblock.validation.alloyforgeinterior"));
			return false;
		}
	}

	@Override
	public int getMinimumXSize() {
		return 5;
	}

	@Override
	public int getMinimumZSize() {
		return 5;
	}

	@Override
	public int getMinimumYSize() {
		return 6;
	}

	@Override
	public int getMaximumXSize() {
		return 5;
	}

	@Override
	public int getMaximumZSize() {
		return 5;
	}

	@Override
	public int getMaximumYSize() {
		return 6;
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
		primaryTank.readFromNBT(data.getCompoundTag("fluidInput1"));
		secondaryTank.readFromNBT(data.getCompoundTag("fluidInput2"));
		outputTank.readFromNBT(data.getCompoundTag("output"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("fluidInput1", primaryTank.writeToNBT(new NBTTagCompound()));
		data.setTag("fluidInput2", secondaryTank.writeToNBT(new NBTTagCompound()));
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

	@Override
	public void updateFluid(FluidStack fluid) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Alloy Forge";
	}
}
