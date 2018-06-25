package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.SlotHandlerOutput;

public class ContainerMultiblockInventory extends ContainerBase {
	public ContainerMultiblockInventory(EntityPlayer player, SARMultiblockInventory controller) {
		int slotID = 0;
		if(controller.inventory.itemInput != null) {
			for(int i = 0; i < controller.inventory.itemInput.getHandler().getSlots(); i++) {
				this.addSlotToContainer(new SlotItemHandler(controller.inventory.itemInput.getHandler(), slotID++,
						controller.inventory.itemInput.getX(i), controller.inventory.itemInput.getY(i)));
			}
		}
		if(controller.inventory.itemOutput != null) {
			for(int i = 0; i < controller.inventory.itemOutput.getHandler().getSlots(); i++) {
				this.addSlotToContainer(new SlotHandlerOutput(controller.inventory.itemOutput.getHandler(), slotID++,
						controller.inventory.itemOutput.getX(i), controller.inventory.itemOutput.getY(i)));
			}
		}
		createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
