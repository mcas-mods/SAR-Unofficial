package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;

public class TileEntitySpinGenerator extends TileEntitySpinMachine {

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;
		((ISpinHandler) this.getWorld().getTileEntity(this.getPos().up())).spin(100);
	}
}
