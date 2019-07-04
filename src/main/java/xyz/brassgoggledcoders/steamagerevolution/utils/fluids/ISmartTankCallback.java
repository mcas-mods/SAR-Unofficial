package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;

public interface ISmartTankCallback<I extends IMachineHasInventory> {
	void onTankContentsChanged(FluidTankSmart tank, IOType type, I parent);

	@SideOnly(Side.CLIENT)
	void updateFluid(PacketFluidUpdate message);

	@SideOnly(Side.CLIENT)
	@Deprecated
	default void updateFluid(PacketMultiFluidUpdate message) {

	}
}
