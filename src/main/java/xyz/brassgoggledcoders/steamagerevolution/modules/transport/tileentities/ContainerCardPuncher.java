package xyz.brassgoggledcoders.steamagerevolution.modules.transport.tileentities;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.ItemPunchcard;

public class ContainerCardPuncher extends ContainerBase {

	public ContainerCardPuncher(ItemStackHandler codeInv, InventoryPlayer playerInv) {
		this.addSlotToContainer(new SlotCard(codeInv, 0, 25, 33));
		this.addSlotToContainer(new SlotItemHandler(codeInv, 1, 25, 45));
		this.addSlotToContainer(new SlotCard(codeInv, 2, 134, 33));
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
