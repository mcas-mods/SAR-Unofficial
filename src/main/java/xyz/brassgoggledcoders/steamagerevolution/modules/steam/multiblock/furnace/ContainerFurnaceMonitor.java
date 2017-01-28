package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFurnaceMonitor extends ContainerBase {
	public ContainerFurnaceMonitor(EntityPlayer player, TileEntityFurnaceMonitor tile) {
		ControllerSteamFurnace c = ((ControllerSteamFurnace) tile.getMultiblockController());
		this.addSlotToContainer(new SlotItemHandler(c.inputInventory, 0, 0, 0));
		this.addSlotToContainer(new SlotItemHandler(c.inputInventory, 1, 20, 0));
		this.addSlotToContainer(new SlotItemHandler(c.inputInventory, 2, 40, 0));

		this.addSlotToContainer(new SlotItemHandler(c.outputInventory, 0, 0, 30));
		this.addSlotToContainer(new SlotItemHandler(c.outputInventory, 1, 20, 30));
		this.addSlotToContainer(new SlotItemHandler(c.outputInventory, 2, 40, 30));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
}
