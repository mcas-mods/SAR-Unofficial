package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class ControllerSorter extends RectangularMultiblockControllerBase {

	public ItemStackHandler inventory = new ItemStackHandler(8);
	private int numberOfRateUpgrades = 0;
	private BiMap<BlockPos, Integer> bufferPositions;

	protected ControllerSorter(World world) {
		super(world);
		bufferPositions = HashBiMap.create();
	}

	@Override
	protected boolean updateServer() {
		int rate = 1;
		if(numberOfRateUpgrades > 0) {
			// There's probably a more efficient, mathier way to do this.
			for(int i = 0; i < numberOfRateUpgrades; i++) {
				rate *= 2;
			}
		}

		// TODO: Take this out of tick loop
		for(IMultiblockPart part : this.connectedParts) {
			if(part instanceof TileEntitySorterBuffer) {
				TileEntitySorterBuffer buffer = (TileEntitySorterBuffer) part;
				if(buffer.color != 0) {
					if(!bufferPositions.containsValue(buffer.color)) {
						bufferPositions.put(buffer.getWorldPosition(), buffer.color);
					}
				}
			}
		}

		for(int i = 0; i < inventory.getSlots(); i++) {
			ItemStack card = inventory.getStackInSlot(i);
			if(card.hasTagCompound()) {
				int fromColour = card.getTagCompound().getInteger("from");
				int toColour = card.getTagCompound().getInteger("to");
				BlockPos fromPosition = bufferPositions.inverse().get(fromColour);
				BlockPos toPosition = bufferPositions.inverse().get(toColour);
				if(fromPosition == null || toPosition == null || WORLD.getTileEntity(toPosition) == null
						|| WORLD.getTileEntity(toPosition) == null)
					return false;
				TileEntitySorterBuffer in = (TileEntitySorterBuffer) WORLD.getTileEntity(fromPosition);
				TileEntitySorterBuffer out = (TileEntitySorterBuffer) WORLD.getTileEntity(toPosition);

				// If so, transfer
				for(int i2 = 0; i2 < in.inventory.getSlots(); i2++) {
					if(!(in.inventory.getStackInSlot(i2).isEmpty())) {
						ItemStack toTransfer = in.inventory.getStackInSlot(i2).copy();
						toTransfer.shrink(rate);
						if(in.inventory.extractItem(i2, rate, true) != null
								&& ItemHandlerHelper.insertItem(out.inventory, toTransfer, true) == null) {
							in.inventory.extractItem(i2, rate, false);
							ItemHandlerHelper.insertItem(out.inventory, toTransfer, false);
							return false; // Nothing about the multiblock *controller* has changed.
						}
					}
				}
			}

		}
		return false;
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if(newPart instanceof TileEntitySorterRateUpgrade) {
			numberOfRateUpgrades++;
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntitySorterBuffer) {
			bufferPositions.remove(oldPart.getWorldPosition());
		}
		else if(oldPart instanceof TileEntitySorterRateUpgrade) {
			numberOfRateUpgrades--;
		}
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		inventory.deserializeNBT(data.getCompoundTag("inventory"));
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
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getMaximumXSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	protected int getMaximumZSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	protected int getMaximumYSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
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
		return false;
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {

	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("inventory", inventory.serializeNBT());

	}

}
