package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.ArrayList;

import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySlowTick;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.SpinUtils;

public class TileEntityInfiniteSpinDrain extends TileEntitySlowTick {

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		ArrayList<ISpinHandler> handlers = SpinUtils.getHandlersNearby(this.getWorld(), this.getPos());

		for(ISpinHandler handler : handlers) {
			handler.decrSpeed(handler.getSpeed());
			this.mod.getLogger().devInfo("Speed voided");
		}
	}
}