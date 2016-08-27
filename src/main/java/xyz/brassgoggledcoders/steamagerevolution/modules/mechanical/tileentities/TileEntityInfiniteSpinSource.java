package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

public class TileEntityInfiniteSpinSource extends TileEntitySpinGenerator {

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		this.handler.setSpeed(Integer.MAX_VALUE);

		super.updateTile();
	}
}
