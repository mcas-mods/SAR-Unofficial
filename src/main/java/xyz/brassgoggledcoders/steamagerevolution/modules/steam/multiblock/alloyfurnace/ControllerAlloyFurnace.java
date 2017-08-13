package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace;

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
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ModuleSteam;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.SARRectangularMultiblockControllerBase;

public class ControllerAlloyFurnace extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	public FluidTank steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this);
	public FluidTank primaryTank = new FluidTankSmart(TileEntityCastingBench.VALUE_BLOCK * 16, this);
	public FluidTank secondaryTank = new FluidTankSmart(TileEntityCastingBench.VALUE_BLOCK * 16, this);
	public ItemStackHandler inputSolid = new ItemStackHandler();
	public FluidTank outputTank = new FluidTank(Fluid.BUCKET_VOLUME * 16);

	public boolean isHardened = true;
	// public int temperature = 0;

	public static final int steamUsePerHeat = Fluid.BUCKET_VOLUME / 10;
	public static final int steamUsePerMaintain = Fluid.BUCKET_VOLUME / 100;
	// public static final int maxTemperature = 1000;

	public ControllerAlloyFurnace(World world) {
		super(world, ModuleSteam.alloyFurnaceFluidOutput);
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;

		boolean hasFirstFluid = primaryTank.getFluid() != null;
		boolean hasSecondFluid = secondaryTank.getFluid() != null;
		boolean hasItems = !inputSolid.getStackInSlot(0).isEmpty();

		// Can't do anything without a base for the alloy
		if(hasFirstFluid) {
			// TODO cleanup logic - a lot is repeated between recipe 'types'
			if(hasSecondFluid) {
				AlloyFurnaceRecipe r = AlloyFurnaceRecipe.getRecipe(primaryTank.getFluid(), secondaryTank.getFluid());
				if(r != null && (!r.requiresHardCase || isHardened)) {
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
		}
		if(hasItems) {
			AlloyFurnaceRecipe r = AlloyFurnaceRecipe.getRecipe(primaryTank.getFluid(), inputSolid.getStackInSlot(0));
			if(r != null && (!r.requiresHardCase || isHardened)) {
				if(primaryTank.getFluidAmount() >= r.primaryInput.amount) {
					// FMLLog.warning("Current: " + inputSolid.getStackInSlot(0).getCount());
					// FMLLog.warning("Desired: " + r.secondaryInput.getCount());
					if(inputSolid.getStackInSlot(0).getCount() >= r.secondaryInputItem.getCount()) {
						// FMLLog.warning("C2");
						if(outputTank.fill(r.output, false) == r.output.amount) {
							// FMLLog.warning("C3");
							primaryTank.drain(r.primaryInput.amount, true);
							inputSolid.extractItem(0, r.secondaryInputItem.getCount(), false);
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
		isHardened = data.getBoolean("hardened");
		inputSolid.deserializeNBT(data.getCompoundTag("solidInput"));
		primaryTank.readFromNBT(data.getCompoundTag("fluidInput1"));
		secondaryTank.readFromNBT(data.getCompoundTag("fluidInput2"));
		outputTank.readFromNBT(data.getCompoundTag("output"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setBoolean("hardened", isHardened);
		data.setTag("solidInput", inputSolid.serializeNBT());
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
