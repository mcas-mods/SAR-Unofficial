package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import net.minecraftforge.fluids.FluidTank;

public interface ISmartTankCallback {
	void onTankContentsChanged(FluidTank tank);
}
