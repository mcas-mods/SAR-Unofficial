package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceHandler;

public class ContainerInventory extends ContainerBase {
	public ContainerInventory(EntityPlayer player, IHasInventory recipeTileEntity) {
		if(!recipeTileEntity.getInventory().getItemHandlers().isEmpty()) {
			for(InventoryPieceHandler<? extends ItemStackHandler> iPiece : recipeTileEntity.getInventory().itemPieces
					.values()) {
				for(int i = 0; i < iPiece.getHandler().getSlots(); i++) {
					addSlotToContainer(new SlotItemHandler(iPiece.getHandler(), i, iPiece.getX(i), iPiece.getY(i)));
				}
			}
		}
		createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	// TODO
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}

}
