package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import java.util.LinkedHashMap;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.boilerplate.api.IDebuggable;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public class TileEntityBeltEnd extends TileEntityOneWayPair implements IDebuggable {

	private ISpinHandler handler = new SpinHandler();
	private int spinPer = 10;

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == SARAPI.SPIN_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capObject, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capObject, EnumFacing side) {
		if(capObject == SARAPI.SPIN_HANDLER_CAPABILITY) {
			return SARAPI.SPIN_HANDLER_CAPABILITY.cast(handler);
		}

		return super.getCapability(capObject, side);
	}

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		// this.handler.fill(1);

		if(this.isTilePaired()) {
			if((this.isMaster() && this.getPairedTile() != null)) {
				this.handler.transferSpin(this.handler, ((TileEntityBeltEnd) this.getPairedTile()).handler, spinPer,
						0.5F);
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
