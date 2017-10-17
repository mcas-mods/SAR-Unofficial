package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

public class ContainerFluidIO extends ContainerBase {
	public ContainerFluidIO(EntityPlayer player, TileEntityFluidIO tile) {
		this.addSlotToContainer(new SlotFluidContainer(
				tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), 0, 25, 33));
		this.addSlotToContainer(new SlotFluidContainer(
				tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), 1, 134, 33));
		this.createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}

}
