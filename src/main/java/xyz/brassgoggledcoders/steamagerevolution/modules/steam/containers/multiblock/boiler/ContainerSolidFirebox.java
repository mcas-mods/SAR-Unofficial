package xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler;

import com.teamacroynmcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler.TileEntitySolidFirebox;

public class ContainerSolidFirebox extends ContainerBase {
	public ContainerSolidFirebox(EntityPlayer player, TileEntitySolidFirebox tile) {
		IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		this.addSlotToContainer(new SlotFuel(inventory, tile, 0, 56, 53));

		this.createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
