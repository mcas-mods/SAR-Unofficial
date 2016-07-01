package xyz.brassgoggledcoders.steamagerevolution.api;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class SARAPI {

	@CapabilityInject(ISpinHandler.class)
	public static final Capability<ISpinHandler> SPIN_HANDLER_CAPABILITY = null;
}
