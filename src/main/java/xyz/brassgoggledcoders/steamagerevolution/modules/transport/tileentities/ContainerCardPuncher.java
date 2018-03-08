package xyz.brassgoggledcoders.steamagerevolution.modules.transport.tileentities;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.ItemPunchcard;

public class ContainerCardPuncher extends ContainerBase {

	public ContainerCardPuncher(TileEntityCardPuncher tile, InventoryPlayer playerInv) {
		ItemStackHandler codeInv = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				.cast(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
		// Card in
		this.addSlotToContainer(new SlotCard(codeInv, 0, 25, 33));
		// Card out
		this.addSlotToContainer(new SlotCard(codeInv, 1, 134, 33));
		// Dye
		this.addSlotToContainer(new SlotItemHandler(codeInv, 2, 25, 45));
		// Items
		for(int i = 3; i < 13; i++) {
			this.addSlotToContainer(new SlotItemHandler(codeInv, i, 25 + i, 45));
		}
		this.createPlayerSlots(playerInv);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	public static class SlotCard extends SlotItemHandler {
		public SlotCard(IItemHandler itemHandler, int index, int xPos, int yPos) {
			super(itemHandler, index, xPos, yPos);
		}

		@Override
		public boolean isItemValid(@Nullable ItemStack stack) {
			return stack != null && stack.isEmpty() && stack.getItem() instanceof ItemPunchcard;
		}
	}

}
