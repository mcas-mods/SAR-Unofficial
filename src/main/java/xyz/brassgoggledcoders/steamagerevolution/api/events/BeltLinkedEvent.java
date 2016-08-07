package xyz.brassgoggledcoders.steamagerevolution.api.events;

import net.minecraftforge.fml.common.eventhandler.Event;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntityBeltEnd;

// TODO Cancelable
public class BeltLinkedEvent extends Event {
	// TODO Passing TileEntityBeltEnd not TileEntity creates dependency...
	public BeltLinkedEvent(TileEntityBeltEnd start, TileEntityBeltEnd end) {}
}
