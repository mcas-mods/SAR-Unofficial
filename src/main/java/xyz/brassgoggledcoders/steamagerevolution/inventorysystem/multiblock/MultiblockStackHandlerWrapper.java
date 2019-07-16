package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;

public class MultiblockStackHandlerWrapper implements IItemHandler, IItemHandlerModifiable {

	final MultiblockInventoryTileEntity<? extends MultiblockCraftingMachine<? extends InventoryCraftingMachine>> tile;
	final String handlerName;

	public MultiblockStackHandlerWrapper(
			MultiblockInventoryTileEntity<? extends MultiblockCraftingMachine<? extends InventoryCraftingMachine>> tile,
			String handlerName) {
		this.tile = tile;
		this.handlerName = handlerName;
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler() != null) {
			tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler().setStackInSlot(slot,
					stack);
		}
	}

	@Override
	public int getSlots() {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler().getSlots();
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler()
					.getStackInSlot(slot);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler().insertItem(slot,
					stack, simulate);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler()
					.extractItem(slot, amount, simulate);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public int getSlotLimit(int slot) {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getItemPiece(handlerName).getHandler()
					.getSlotLimit(slot);
		}
		return 0;
	}

}
