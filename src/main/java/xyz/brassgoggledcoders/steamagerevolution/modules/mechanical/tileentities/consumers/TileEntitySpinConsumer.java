package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers;

import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntitySpinMachine;

public abstract class TileEntitySpinConsumer extends TileEntitySpinMachine {

	@Override
	public void updateTile() {
		// Spin down
		if(this.handler.getSpeed() > 0)
			this.handler.decrSpeed();
	}

}
