package xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.MultiblockOreWrapper;

public class TileEntityGrinderInput extends TileEntityGrinderPart {

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == SARCapabilities.SEMISOLID_HANDLER || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == SARCapabilities.SEMISOLID_HANDLER) {
			return SARCapabilities.SEMISOLID_HANDLER.cast(new MultiblockOreWrapper(this));
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}

}
