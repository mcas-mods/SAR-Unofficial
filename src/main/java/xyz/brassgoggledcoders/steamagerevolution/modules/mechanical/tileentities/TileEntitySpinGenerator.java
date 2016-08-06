package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.ArrayList;

import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.SpinUtils;

public abstract class TileEntitySpinGenerator extends TileEntitySpinMachine {

	private ArrayList<ISpinHandler> output_locations;

	@Override
	public void updateTile() {
		ArrayList<ISpinHandler> handlers = SpinUtils.getHandlersNearby(this.getWorld(), this.getPos());

		for(ISpinHandler other_handler : handlers) {
			if(this.handler.getSpeed() > other_handler.getSpeed()) {
				other_handler.setSpeed(this.handler.getSpeed());
			}
		}
	}
}
