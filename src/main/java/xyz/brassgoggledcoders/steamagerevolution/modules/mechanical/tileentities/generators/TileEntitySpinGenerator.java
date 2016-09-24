package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.generators;

import java.util.ArrayList;

import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntitySpinMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.SpinUtils;

public abstract class TileEntitySpinGenerator extends TileEntitySpinMachine {

	public TileEntitySpinGenerator() {
		super();
	}

	@Override
	protected void onSpeedChanged(int lastSpeed, int newSpeed) {
		if(lastSpeed < newSpeed) {
			updatesUntilSpindown = 10;
		}
		this.markDirty();
		this.sendBlockUpdate();
	}

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
