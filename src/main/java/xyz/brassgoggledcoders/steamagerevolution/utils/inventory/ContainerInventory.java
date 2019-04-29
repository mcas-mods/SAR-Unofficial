package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import com.teamacronymcoders.base.containers.ContainerBase;
import com.teamacronymcoders.base.util.inventory.SlotHandlerOutput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerInventory extends ContainerBase {
	public ContainerInventory(EntityPlayer player, IMachineHasInventory holder) {
		if (holder.getInventory().itemInput != null) {
			for (int i = 0; i < holder.getInventory().itemInput.getHandler().getSlots(); i++) {
				addSlotToContainer(new SlotItemHandler(holder.getInventory().itemInput.getHandler(), i,
						holder.getInventory().itemInput.getX(i), holder.getInventory().itemInput.getY(i)));
			}
		}
		if (holder.getInventory().itemOutput != null) {
			for (int i = 0; i < holder.getInventory().itemOutput.getHandler().getSlots(); i++) {
				addSlotToContainer(new SlotHandlerOutput(holder.getInventory().itemOutput.getHandler(), i,
						holder.getInventory().itemOutput.getX(i), holder.getInventory().itemOutput.getY(i)));
			}
		}
		createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
