package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.ArrayList;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.SpinUtils;

public class TileEntityBeltEnd extends TileEntityPaired {

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == SARAPI.SPIN_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capObject, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capObject, EnumFacing side) {
		if(capObject == SARAPI.SPIN_HANDLER_CAPABILITY)
			return SARAPI.SPIN_HANDLER_CAPABILITY.cast(handler);

		return super.getCapability(capObject, side);
	}

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		ArrayList<ISpinHandler> handlers = SpinUtils.getHandlersNearby(this.getWorld(), this.getPos());

		for(ISpinHandler handler : handlers) {
			// if(handler.getSpeed() > this.handler.getSpeed())
			this.handler.setSpeed(handler.getSpeed());
		}

		if(this.isTilePaired())
			if(this.isMaster() && this.getPairedTile() != null)
				this.getPairedTile().getCapability(SARAPI.SPIN_HANDLER_CAPABILITY, null).setSpeed(handler.getSpeed());
	}
}
