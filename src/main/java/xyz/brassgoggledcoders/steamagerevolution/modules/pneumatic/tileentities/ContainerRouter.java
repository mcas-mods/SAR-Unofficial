package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.tileentities;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerRouter extends ContainerBase {
	public ContainerRouter(EntityPlayer player, TileEntityRouter tile) {
		this.addSlotToContainer(new SlotItemHandler(
				tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), 0, 25, 33));
		this.createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
