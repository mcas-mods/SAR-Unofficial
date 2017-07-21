package xyz.brassgoggledcoders.steamagerevolution.utils;

import net.minecraftforge.fluids.FluidTank;

public interface ISmartTankCallback {
	void onTankContentsChanged(FluidTank tank);
}
