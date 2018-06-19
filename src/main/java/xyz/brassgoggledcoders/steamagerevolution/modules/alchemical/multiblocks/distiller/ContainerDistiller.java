package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.SlotHandlerOutput;

public class ContainerDistiller extends ContainerBase {
	public ContainerDistiller(EntityPlayer player, ControllerDistiller tile) {
		this.addSlotToContainer(new SlotHandlerOutput(tile.itemOutput, 0, 149, 32));
		this.createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
