package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARRectangularMultiblockControllerBase;

public class ControllerSorter extends SARRectangularMultiblockControllerBase {

	public ItemStackHandler cards = new ItemStackHandler(8);
	public ItemStackHandler buffer = new ItemStackHandler(16);
	private int numberOfRateUpgrades = 0;
	private BiMap<BlockPos, Integer> outputPositions;

	protected ControllerSorter(World world) {
		super(world);
		outputPositions = HashBiMap.create();
	}

	@Override
	public ItemStackHandler getInventory(String toWrap) {
		return buffer;
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
			if(part instanceof TileEntitySorterOutput) {
				TileEntitySorterOutput buffer = (TileEntitySorterOutput) part;
				if(buffer.color != 0) {
					if(!outputPositions.containsValue(buffer.color)) {
						outputPositions.put(buffer.getWorldPosition(), buffer.color);
					}
				}
			}
		}

		for(int i = 0; i < cards.getSlots(); i++) {
			ItemStack card = cards.getStackInSlot(i);
			if(card.hasTagCompound()) {
				int fromColour = card.getTagCompound().getInteger("from");
				int toColour = card.getTagCompound().getInteger("to");
				BlockPos fromPosition = outputPositions.inverse().get(fromColour);
				BlockPos toPosition = outputPositions.inverse().get(toColour);
				if(fromPosition == null || toPosition == null || WORLD.getTileEntity(toPosition) == null
						|| WORLD.getTileEntity(toPosition) == null)
					return false;
				TileEntitySorterOutput in = (TileEntitySorterOutput) WORLD.getTileEntity(fromPosition);
				TileEntitySorterOutput out = (TileEntitySorterOutput) WORLD.getTileEntity(toPosition);

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
		if(oldPart instanceof TileEntitySorterOutput) {
			outputPositions.remove(oldPart.getWorldPosition());
		}
		else if(oldPart instanceof TileEntitySorterRateUpgrade) {
			numberOfRateUpgrades--;
		}
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		cards.deserializeNBT(data.getCompoundTag("cards"));
		buffer.deserializeNBT(data.getCompoundTag("buffer"));
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
	public int getMaximumXSize() {
		// TODO Auto-generated method stub
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
		data.setTag("cards", cards.serializeNBT());
		data.setTag("buffer", buffer.serializeNBT());
	}

	@Override
	public String getName() {
		return "Sorter";
	}

}
