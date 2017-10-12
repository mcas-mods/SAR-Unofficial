package xyz.brassgoggledcoders.steamagerevolution.utils;

import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;

public interface ISmartTankCallback {
	void onTankContentsChanged(FluidTank tank);

	@SideOnly(Side.CLIENT)
	void updateFluid(PacketFluidUpdate message);

	@SideOnly(Side.CLIENT)
	default void updateFluid(PacketMultiFluidUpdate message) {

	};
}
