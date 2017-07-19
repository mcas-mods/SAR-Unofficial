package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityCastingBench;

public class ControllerAlloyFurnace extends RectangularMultiblockControllerBase implements ISmartTankCallback {

	public FluidTank steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this);
	public FluidTank inputTank = new FluidTank(TileEntityCastingBench.VALUE_BLOCK * 16);
	public ItemStackHandler itemInv = new ItemStackHandler(3);
	public FluidTank outputTank = new FluidTank(Fluid.BUCKET_VOLUME * 16);

	public int carbonLevel = 0;
	public boolean isHardened = true;

	public static final int steamUsePerOperation = Fluid.BUCKET_VOLUME / 10;

	public ControllerAlloyFurnace(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;
		if(inputTank.getFluid() != null && AlloyFurnaceRecipe.getRecipe(inputTank.getFluid()) != null) {
			AlloyFurnaceRecipe r = AlloyFurnaceRecipe.getRecipe(inputTank.getFluid());
			if(!r.requiresHardCase || this.isHardened) {
				if((r.input.isFluidEqual(inputTank.getFluid()) && r.input.amount >= inputTank.getFluidAmount())) {
					if(outputTank.getFluid() == null || (r.output.isFluidEqual(outputTank.getFluid())
							&& r.output.amount <= (outputTank.getCapacity() - outputTank.getFluidAmount()))) {
						outputTank.fill(r.output, true);
						inputTank.drain(r.input, true);
						flag = true;
					}
				}
			}
		}

		// for(int i = 0; i < itemInv.getSlots(); i++) {
		// if(!itemInv.getStackInSlot(i).isEmpty()) {
		// if(itemInv.getStackInSlot(i).getItem() == Items.COAL) {
		// carbonLevel += 9;
		// itemInv.extractItem(i, 1, false);
		// flag = true;
		// }
		// }
		// }
		//
		// if(carbonLevel > 0
		// && inputTank.getFluidAmount() > ((TileEntityCastingBench.VALUE_BLOCK * 9)
		// + TileEntityCastingBench.VALUE_NUGGET)
		// && outputTank.getFluidAmount() != outputTank.getCapacity()
		// && steamTank.getFluidAmount() >= steamUsePerOperation) {
		//
		// FluidStack toInsert =
		// new FluidStack(FluidRegistry.getFluid("iron"/* TODO */), TileEntityCastingBench.VALUE_NUGGET);
		// if(outputTank.fill(toInsert, false) == TileEntityCastingBench.VALUE_NUGGET) {
		// inputTank.drain(TileEntityCastingBench.VALUE_NUGGET, true);
		// outputTank.fill(toInsert, true);
		// steamTank.drain(steamUsePerOperation, true);
		// carbonLevel--;
		// flag = true;
		// }
		//
		// }

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
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		// TODO Auto-generated method stub

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
	public void readFromDisk(NBTTagCompound data) {
		data.setBoolean("hardened", isHardened);
		carbonLevel = data.getInteger("carbon");
		itemInv.deserializeNBT(data.getCompoundTag("inv"));
		inputTank.readFromNBT(data.getCompoundTag("input"));
		outputTank.readFromNBT(data.getCompoundTag("output"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setBoolean("hardened", isHardened);
		data.setInteger("carbon", carbonLevel);
		data.setTag("inv", itemInv.serializeNBT());
		data.setTag("input", inputTank.writeToNBT(new NBTTagCompound()));
		data.setTag("output", outputTank.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void readFromUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

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

}
