package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityCrucible;

public class ControllerSmeltery extends RectangularMultiblockControllerBase {

	public FluidTank steamTank = new FluidTank(Fluid.BUCKET_VOLUME * 16);
	public FluidTank ironTank = new FluidTank(Fluid.BUCKET_VOLUME * 16);
	public ItemStackHandler coalInv = new ItemStackHandler(3);
	public FluidTank steelTank = new FluidTank(Fluid.BUCKET_VOLUME * 16);

	public int carbonLevel = 0;

	public ControllerSmeltery(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;

		for(int i = 0; i < coalInv.getSlots(); i++) {
			if(ItemStackUtils.isItemNonNull(coalInv.getStackInSlot(i))) {
				if(coalInv.getStackInSlot(i).getItem() == Items.COAL) {
					carbonLevel += 9;
					coalInv.extractItem(i, 1, false);
					flag = true;
				}
			}
		}

		if(carbonLevel > 0 && ironTank.getFluidAmount() > TileEntityCrucible.VALUE_NUGGET
				&& steelTank.getFluidAmount() != steelTank.getCapacity()) {
			if(ironTank.drain(TileEntityCrucible.VALUE_NUGGET, false).amount == TileEntityCrucible.VALUE_NUGGET) {
				FluidStack toInsert =
						new FluidStack(FluidRegistry.getFluid("iron"/* TODO */), TileEntityCrucible.VALUE_NUGGET);
				if(steelTank.fill(toInsert, false) == TileEntityCrucible.VALUE_NUGGET) {
					ironTank.drain(TileEntityCrucible.VALUE_NUGGET, true);
					steelTank.fill(toInsert, true);
					carbonLevel--;
					flag = true;
				}
			}
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
		return 5;
	}

	@Override
	protected int getMinimumZSize() {
		return 5;
	}

	@Override
	protected int getMinimumYSize() {
		return 9;
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
	public void readFromDisk(NBTTagCompound data) {
		carbonLevel = data.getInteger("carbon");
		coalInv.deserializeNBT(data.getCompoundTag("inv"));
		ironTank.readFromNBT(data.getCompoundTag("iron"));
		steelTank.readFromNBT(data.getCompoundTag("steel"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setInteger("carbon", carbonLevel);
		data.setTag("inv", coalInv.serializeNBT());
		data.setTag("iron", ironTank.writeToNBT(new NBTTagCompound()));
		data.setTag("steel", steelTank.writeToNBT(new NBTTagCompound()));
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

}
