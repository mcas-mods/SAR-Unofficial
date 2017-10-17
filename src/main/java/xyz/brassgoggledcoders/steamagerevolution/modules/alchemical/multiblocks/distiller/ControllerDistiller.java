package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.ModuleAlchemical;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARRectangularMultiblockControllerBase;

public class ControllerDistiller extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	public static int tankCapacity = Fluid.BUCKET_VOLUME * 8;

	public FluidTankSmart fluidInput;
	public FluidTankSmart fluidOutput;
	public ItemStackHandler itemOutput;
	public FluidTankSingleSmart steamTank;
	int ticks = 0;
	// int temperature = 0;
	DistillerRecipe currentRecipe = null;

	// private static final int temperatureThreshold = 110;
	private static final int steamPerHeat = Fluid.BUCKET_VOLUME / 4;

	protected ControllerDistiller(World world) {
		super(world);
		fluidInput = new FluidTankSmart(tankCapacity, this);
		fluidOutput = new FluidTankSmart(tankCapacity, this);
		itemOutput = new ItemStackHandler();
		steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this);
	}

	@Override
	public String getName() {
		return "Distiller";
	}

	@Override
	protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
		BlockPos first = this.getMinimumCoord();
		BlockPos second = new BlockPos(this.getMaximumCoord().getX(), this.getMinimumCoord().getY(),
				this.getMaximumCoord().getZ());

		// Get all blocks in bottom layer of machine & check they're radiators
		for(BlockPos pos : BlockPos.getAllInBox(first, second)) {
			if(WORLD.getBlockState(pos).getBlock() != ModuleAlchemical.distiller_radiator) {
				validatorCallback.setLastError(
						new ValidationError("steamagerevolution.multiblock.validation.distiller_radiator"));
				return false;
			}
		}
		// Same for second layer, check they're hotplates
		for(BlockPos pos : BlockPos.getAllInBox(first.up(), second.up())) {
			if(WORLD.getBlockState(pos).getBlock() != ModuleAlchemical.distiller_hotplate) {
				validatorCallback.setLastError(
						new ValidationError("steamagerevolution.multiblock.validation.distiller_hotplate"));
				return false;
			}
		}

		return super.isMachineWhole(validatorCallback);
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		fluidInput.readFromNBT(data.getCompoundTag("input"));
		fluidOutput.readFromNBT(data.getCompoundTag("output"));
		itemOutput.deserializeNBT(data.getCompoundTag("itemOutput"));
		ticks = data.getInteger("progress");
		// temperature = data.getInteger("temperature");
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
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 34;
	}

	@Override
	public int getMinimumXSize() {
		return 3;
	}

	@Override
	public int getMinimumYSize() {
		return 5;
	}

	@Override
	public int getMinimumZSize() {
		return 3;
	}

	@Override
	public int getMaximumXSize() {
		return 10;
	}

	@Override
	public int getMaximumZSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getMaximumYSize() {
		// TODO Auto-generated method stub
		return 6;
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
	protected boolean updateServer() {
		if(steamTank.getFluidAmount() >= steamPerHeat) {
			steamTank.drain(steamPerHeat, true);
			if(currentRecipe == null) {
				if(fluidInput.getFluidAmount() > 0 && DistillerRecipe.getRecipe(fluidInput.getFluid()) != null) {
					currentRecipe = DistillerRecipe.getRecipe(fluidInput.getFluid());
					return true;
				}
			}
			else {
				if(ticks == currentRecipe.ticksToProcess) {
					if(fluidOutput.fill(currentRecipe.output, false) == currentRecipe.output.amount
							&& ItemHandlerHelper.insertItem(itemOutput, currentRecipe.itemOutput, true).isEmpty()) {
						fluidOutput.fill(currentRecipe.output, true);
						fluidInput.drain(currentRecipe.input, true);
						ItemHandlerHelper.insertItem(itemOutput, currentRecipe.itemOutput, false);
						if(fluidOutput.getFluidAmount() == 0) {
							currentRecipe = null;
						}
						return true;
					}
				}
				else {
					ticks++;
					return true;
				}
			}
		}
		return false;
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
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return WORLD.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("input", fluidInput.writeToNBT(new NBTTagCompound()));
		data.setTag("output", fluidOutput.writeToNBT(new NBTTagCompound()));
		data.setTag("itemOutput", itemOutput.serializeNBT());
		data.setInteger("progress", ticks);
		// data.setInteger("temperature", temperature);
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		// TODO Auto-generated method stub

	}

	@Override
	protected FluidTank getTank(String toWrap) {
		if(toWrap.equals("input")) {
			return fluidInput;
		}
		else if(toWrap.equals("output")) {
			return fluidOutput;
		}
		return steamTank;
	}

}
