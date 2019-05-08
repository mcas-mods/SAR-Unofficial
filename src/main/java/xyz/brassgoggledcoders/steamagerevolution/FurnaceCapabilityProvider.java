package xyz.brassgoggledcoders.steamagerevolution;

import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import xyz.brassgoggledcoders.steamagerevolution.api.FurnaceFumeProducer;

public class FurnaceCapabilityProvider implements ICapabilityProvider {

	TileEntityFurnace tile;

	public FurnaceCapabilityProvider(TileEntityFurnace tile) {
		this.tile = tile;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == SARCapabilities.FUME_PRODUCER;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (hasCapability(capability, facing)) {
			return SARCapabilities.FUME_PRODUCER.cast(new FurnaceFumeProducer(tile));
		}
		return null;
	}

}
