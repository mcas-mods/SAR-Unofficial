package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerVat extends ContainerBase {
	public ContainerVat(EntityPlayer player, ControllerVat tile) {
		for(int i = 0; i < tile.itemInput.getSlots(); i++) {
			this.addSlotToContainer(new SlotItemHandler(tile.itemInput, i, 88, 11 + (i * 21)));
		}
		this.createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
