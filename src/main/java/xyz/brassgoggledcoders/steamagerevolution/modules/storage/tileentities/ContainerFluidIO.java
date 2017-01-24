package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.CapabilityItemHandler;

public class ContainerFluidIO extends ContainerBase {
	public ContainerFluidIO(EntityPlayer player, TileEntityFluidIO tile) {
		this.addSlotToContainer(new SlotFluidContainer(
				tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), tile, 0, 25, 33));
		this.addSlotToContainer(new SlotFluidContainer(
				tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), tile, 1, 134, 33));
		this.createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
