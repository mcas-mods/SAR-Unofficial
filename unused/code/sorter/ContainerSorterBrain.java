package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSorterBrain extends ContainerBase {

	public ContainerSorterBrain(ItemStackHandler codeInv, InventoryPlayer playerInv) {
		for(int i = 0; i < 8; i++) {
			addSlotToContainer(new SlotItemHandler(codeInv, i, 30 + 3 * i, 30));
		}
		createPlayerSlots(playerInv);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return true;
	}

}
