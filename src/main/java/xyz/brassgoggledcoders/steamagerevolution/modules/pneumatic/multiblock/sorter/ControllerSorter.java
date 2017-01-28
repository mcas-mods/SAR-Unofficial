package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.multiblock.sorter;

import java.util.HashSet;
import java.util.Set;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class ControllerSorter extends MultiblockControllerBase {

	private Set<TileEntityInputBuffer> attachedInputs;
	private Set<TileEntityOutputBuffer> attachedOutputs;

	protected ControllerSorter(World world) {
		super(world);
		attachedInputs = new HashSet<TileEntityInputBuffer>();
		attachedOutputs = new HashSet<TileEntityOutputBuffer>();
	}

	@Override
	protected boolean updateServer() {
		for(TileEntityInputBuffer in : attachedInputs) {
			for(TileEntityOutputBuffer out : attachedOutputs) {
				// Check if code matches
				for(int i = 0; i < in.code.getSlots(); i++) {
					if(!ItemStackUtils.isItemNonNull(out.code.getStackInSlot(i))
							|| !ItemStackUtils.isItemNonNull(in.code.getStackInSlot(i)) || (!ItemStackUtils
									.doItemsMatch(out.code.getStackInSlot(i), in.code.getStackInSlot(i).getItem()))) {
						return false;
					}
				}
				// If so, transfer
				for(int i2 = 0; i2 < in.inventory.getSlots(); i2++) {
					if(ItemStackUtils.isItemNonNull(in.inventory.getStackInSlot(i2))) {
						ItemStack toTransfer = in.inventory.getStackInSlot(i2).copy();
						toTransfer.stackSize = 1;
						if(in.inventory.extractItem(i2, 1, true) != null
								&& ItemHandlerHelper.insertItem(out.inventory, toTransfer, true) == null) {
							in.inventory.extractItem(i2, 1, false);
							ItemHandlerHelper.insertItem(out.inventory, toTransfer, false);
							return false; // Nothing about the multiblock *controller* has changed.
						}
					}
				}
				return false;
			}
		}
		return false;
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if(newPart instanceof TileEntityInputBuffer) {
			attachedInputs.add((TileEntityInputBuffer) newPart);
		}
		else if(newPart instanceof TileEntityOutputBuffer) {
			attachedOutputs.add((TileEntityOutputBuffer) newPart);
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntityInputBuffer) {
			attachedInputs.remove(oldPart);
		}
		else if(oldPart instanceof TileEntityOutputBuffer) {
			attachedOutputs.remove(oldPart);
		}
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
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
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

}
