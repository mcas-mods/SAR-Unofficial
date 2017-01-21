package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.tileentities;

import com.teamacroynmcoders.base.containers.ContainerBase;
import com.teamacroynmcoders.base.containers.slots.SlotChanged;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.CapabilityItemHandler;

public class ContainerRouter extends ContainerBase {
	public ContainerRouter(EntityPlayer player, TileEntityRouter tile) {
		this.addSlotToContainer(new SlotChanged(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null),
				tile, 0, 25, 33));
		this.createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
