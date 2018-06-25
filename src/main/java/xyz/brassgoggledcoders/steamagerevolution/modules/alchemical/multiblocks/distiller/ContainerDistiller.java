package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.SlotHandlerOutput;

public class ContainerDistiller extends ContainerBase {
	public ContainerDistiller(EntityPlayer player, ControllerDistiller tile) {
		addSlotToContainer(new SlotHandlerOutput(tile.inventory.getItemOutput(), 0, tile.inventory.itemOutput.getX(),
				tile.inventory.itemOutput.getY()));
		createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
