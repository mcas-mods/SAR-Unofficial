package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui;

import com.teamacronymcoders.base.containers.ContainerBase;
import com.teamacronymcoders.base.util.inventory.SlotHandlerOutput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IMachineHasInventory;

public class ContainerInventory extends ContainerBase {
	public ContainerInventory(EntityPlayer player, IMachineHasInventory holder) {
		if (holder.getInventory().itemInputPiece != null) {
			for (int i = 0; i < holder.getInventory().itemInputPiece.getHandler().getSlots(); i++) {
				addSlotToContainer(new SlotItemHandler(holder.getInventory().itemInputPiece.getHandler(), i,
						holder.getInventory().itemInputPiece.getX(i), holder.getInventory().itemInputPiece.getY(i)));
			}
		}
		if (holder.getInventory().itemOutputPiece != null) {
			for (int i = 0; i < holder.getInventory().itemOutputPiece.getHandler().getSlots(); i++) {
				addSlotToContainer(new SlotHandlerOutput(holder.getInventory().itemOutputPiece.getHandler(), i,
						holder.getInventory().itemOutputPiece.getX(i), holder.getInventory().itemOutputPiece.getY(i)));
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
