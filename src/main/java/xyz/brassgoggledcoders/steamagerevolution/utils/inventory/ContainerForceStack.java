package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IMachineHasInventory;

public class ContainerForceStack extends ContainerBase {
	public ContainerForceStack(EntityPlayer player, IMachineHasInventory holder) {
		if (holder.getInventory().itemInputPiece != null) {
			for (int i = 0; i < holder.getInventory().itemInputPiece.getHandler().getSlots(); i++) {
				addSlotToContainer(new SlotForceStack(holder.getInventory().itemInputPiece.getHandler(), i,
						holder.getInventory().itemInputPiece.getX(i), holder.getInventory().itemInputPiece.getY(i)));
			}
		}
		if (holder.getInventory().itemOutputPiece != null) {
			for (int i = 0; i < holder.getInventory().itemOutputPiece.getHandler().getSlots(); i++) {
				addSlotToContainer(new SlotForceStack(holder.getInventory().itemOutputPiece.getHandler(), i,
						holder.getInventory().itemOutputPiece.getX(i), holder.getInventory().itemOutputPiece.getY(i)));
			}
		}
		createPlayerSlots(player.inventory);
		//Make sure the changes made in HandlerForceStack are sent to the client
		this.detectAndSendChanges();
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	//TODO
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return ItemStack.EMPTY;
    }

}
