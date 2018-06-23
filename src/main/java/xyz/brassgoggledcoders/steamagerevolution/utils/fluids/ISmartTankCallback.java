package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;

public interface ISmartTankCallback {
	default void onTankContentsChanged(FluidTankSmart tank) {
		// Default op provided by MultiblockLogicFramework
	}

	@SideOnly(Side.CLIENT)
	void updateFluid(PacketFluidUpdate message);

	@SideOnly(Side.CLIENT)
	default void updateFluid(PacketMultiFluidUpdate message) {

	}
}
