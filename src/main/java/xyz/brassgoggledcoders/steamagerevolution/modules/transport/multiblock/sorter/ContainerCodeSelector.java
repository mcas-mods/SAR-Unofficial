package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCodeSelector extends ContainerBase {

	public ContainerCodeSelector(ItemStackHandler codeInv, InventoryPlayer playerInv) {
		this.addSlotToContainer(new SlotItemHandler(codeInv, 0, 30, 30));
		this.addSlotToContainer(new SlotItemHandler(codeInv, 1, 60, 30));
		this.addSlotToContainer(new SlotItemHandler(codeInv, 2, 90, 30));
		this.createPlayerSlots(playerInv);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return true;
	}

}
