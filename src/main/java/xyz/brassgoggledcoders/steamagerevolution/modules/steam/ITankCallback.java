package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import net.minecraftforge.fluids.FluidTank;

public interface ITankCallback {
	void onTankContentsChanged(FluidTank tank);
}
