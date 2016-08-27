package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;
import xyz.brassgoggledcoders.steamagerevolution.CapabilityHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public class TileEntityFrictionFirebox extends TileEntityBasicBoilerPart {
	protected final ISpinHandler handler;

	public TileEntityFrictionFirebox() {
		this.handler = new SpinHandler();
	}

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == CapabilityHandler.SPIN_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capObject, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capObject, EnumFacing side) {
		if(capObject == CapabilityHandler.SPIN_HANDLER_CAPABILITY)
			return CapabilityHandler.SPIN_HANDLER_CAPABILITY.cast(handler);

		return super.getCapability(capObject, side);
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}
}
