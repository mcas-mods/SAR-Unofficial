package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.MultiblockTankWrapper;

public class TileEntityVatOutput extends TileEntityVatPart {
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && this.isConnected())
				|| super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new MultiblockTankWrapper(this, "output"));
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}
}
