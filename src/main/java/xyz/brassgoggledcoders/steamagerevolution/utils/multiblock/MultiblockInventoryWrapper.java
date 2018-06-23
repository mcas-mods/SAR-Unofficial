package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class MultiblockInventoryWrapper
		implements IItemHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound> {

	final MultiblockTileEntityBase<SARMultiblockBase> tile;
	final String toWrap;

	public MultiblockInventoryWrapper(MultiblockTileEntityBase<?> tile, String toWrap) {
		this.tile = (MultiblockTileEntityBase<SARMultiblockBase>) tile;
		this.toWrap = toWrap;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory(toWrap) != null) {
			return tile.getMultiblockController().getInventory(toWrap).serializeNBT();
		}
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory(toWrap) != null) {
			tile.getMultiblockController().getInventory(toWrap).deserializeNBT(nbt);
		}
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory(toWrap) != null) {
			tile.getMultiblockController().getInventory(toWrap).setStackInSlot(slot, stack);
		}
	}

	@Override
	public int getSlots() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory(toWrap) != null) {
			return tile.getMultiblockController().getInventory(toWrap).getSlots();
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory(toWrap) != null) {
			return tile.getMultiblockController().getInventory(toWrap).getStackInSlot(slot);
		}
		return null;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory(toWrap) != null) {
			return tile.getMultiblockController().getInventory(toWrap).insertItem(slot, stack, simulate);
		}
		return null;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory(toWrap) != null) {
			return tile.getMultiblockController().getInventory(toWrap).extractItem(slot, amount, simulate);
		}
		return null;
	}

	@Override
	public int getSlotLimit(int slot) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory(toWrap) != null) {
			return tile.getMultiblockController().getInventory(toWrap).getSlotLimit(slot);
		}
		return 0;
	}

}
