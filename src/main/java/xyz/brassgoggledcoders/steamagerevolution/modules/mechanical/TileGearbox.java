package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;

public class TileGearbox extends TileEntity implements ITickable {

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
			return (T) SARAPI.SPIN_HANDLER_CAPABILITY.getDefaultInstance();
		}

		return super.getCapability(capObject, side);
	}

	@Override
	public void update() {
		this.getCapability(SARAPI.SPIN_HANDLER_CAPABILITY, null).fill(null, 1, true);
		FMLLog.warning("" + this.getCapability(SARAPI.SPIN_HANDLER_CAPABILITY, null).getStoredSpin(), "");
	}
}
