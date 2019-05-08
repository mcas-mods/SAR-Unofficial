package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.TankType;

public interface ISmartTankCallback<I extends IMachineHasInventory> {
	void onTankContentsChanged(FluidTankSmart tank, TankType type, I parent);

	@SideOnly(Side.CLIENT)
	void updateFluid(PacketFluidUpdate message);

	@SideOnly(Side.CLIENT)
	default void updateFluid(PacketMultiFluidUpdate message) {

	}
}
