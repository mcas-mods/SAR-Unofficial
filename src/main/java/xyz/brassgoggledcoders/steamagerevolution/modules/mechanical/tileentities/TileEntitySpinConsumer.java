package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

public abstract class TileEntitySpinConsumer extends TileEntitySpinMachine {

	@Override
	public void updateTile() {
		// Spin down
		if(this.handler.getSpeed() > 0)
			this.handler.decrSpeed();
	}

}
