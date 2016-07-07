package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySlowTick;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public abstract class TileEntitySpinMachine extends TileEntitySlowTick {

	private ISpinHandler handler = new SpinHandler();

	// TODO Default behaviour is to attempt to push on all sides, unless wrenched to be pull.

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
}
