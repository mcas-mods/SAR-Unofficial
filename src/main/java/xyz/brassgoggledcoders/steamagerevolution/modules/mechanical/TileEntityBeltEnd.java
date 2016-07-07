package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import java.util.LinkedHashMap;

import xyz.brassgoggledcoders.boilerplate.api.IDebuggable;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public class TileEntityBeltEnd extends TileEntityPaired implements IDebuggable {

	private ISpinHandler handler = new SpinHandler();

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		if(this.isTilePaired()) {
			if((this.isMaster() && this.getPairedTile() != null)) {
				this.handler.transferSpin(this.handler, ((TileEntityBeltEnd) this.getPairedTile()).handler, 0.5F);
				// this.mod.getLogger().devInfo("Master at: " + this.getPos().toString());
			}
			// else
			// this.mod.getLogger().devInfo("Slave at: " + this.getPos().toString());
		}
	}

	@Override
	public LinkedHashMap<String, String> getDebugStrings(LinkedHashMap<String, String> debugStrings) {
		debugStrings.put("spin", "" + this.handler.getStoredSpin());
		return debugStrings;
	}
}
