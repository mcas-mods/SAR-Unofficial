package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces.InventoryPieceHandler;

public class ContainerInventory extends ContainerBase {
	public ContainerInventory(EntityPlayer player, IMachineHasInventory<InventoryRecipe> holder) {
		if(!holder.getInventory().getItemHandlers().isEmpty()) {
			for(int ix = 0; ix < holder.getInventory().itemPieces.size(); ix++) {
				InventoryPieceHandler<ItemStackHandler> iPiece = holder.getInventory().itemPieces.get(0);
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
