package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.ArrayList;

import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.SpinUtils;

public class TileEntityBeltEnd extends TileEntityPaired {

	private float slipAmount = 0.5F;

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		if(this.isTilePaired() && this.getPairedTile() != null) {
			if(this.isMaster()) {
				this.getPairedTile().getCapability(SARAPI.SPIN_HANDLER_CAPABILITY, null)
						.setSpeed(Math.round(handler.getSpeed() * slipAmount));
			}
			else {
				ArrayList<ISpinHandler> handlers = SpinUtils.getHandlersNearby(this.getWorld(), this.getPos());

				for(ISpinHandler other_handler : handlers) {
					if(this.handler.getSpeed() > other_handler.getSpeed()) {
						other_handler.setSpeed(this.handler.getSpeed());
					}
				}
			}
		}
	}
}
