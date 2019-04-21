package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.MultiblockOreWrapper;

public class TileEntityDrillOutput extends TileEntityDrillPart {

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == SARCapabilities.CRUSHED_HANDLER || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == SARCapabilities.CRUSHED_HANDLER) {
			return SARCapabilities.CRUSHED_HANDLER.cast(new MultiblockOreWrapper(this));
		}
		return super.getCapability(capability, facing);
	}
}
