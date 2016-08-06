package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityFrictionHeater extends TileEntitySpinConsumer {
	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {

		return super.hasCapability(capObject, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capObject, EnumFacing side) {

		return super.getCapability(capObject, side);
	}
}
