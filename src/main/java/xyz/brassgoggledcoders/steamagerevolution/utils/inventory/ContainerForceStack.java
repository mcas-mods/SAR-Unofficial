package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerForceStack extends ContainerBase {
	public ContainerForceStack(EntityPlayer player, IMachineHasInventory holder) {
		if (holder.getInventory().itemInput != null) {
			for (int i = 0; i < holder.getInventory().itemInput.getHandler().getSlots(); i++) {
				addSlotToContainer(new SlotForceStack(holder.getInventory().itemInput.getHandler(), i,
						holder.getInventory().itemInput.getX(i), holder.getInventory().itemInput.getY(i)));
			}
		}
		if (holder.getInventory().itemOutput != null) {
			for (int i = 0; i < holder.getInventory().itemOutput.getHandler().getSlots(); i++) {
				addSlotToContainer(new SlotForceStack(holder.getInventory().itemOutput.getHandler(), i,
						holder.getInventory().itemOutput.getX(i), holder.getInventory().itemOutput.getY(i)));
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
