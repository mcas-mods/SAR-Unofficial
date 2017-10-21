package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityMultiblockFluidWrapper<C extends SARRectangularMultiblockControllerBase>
		extends TileEntityMultiblockBase<C> {
	String toWrap;

	public TileEntityMultiblockFluidWrapper(String toWrap, boolean[] validPositions, Class<C> controllerClass,
			Function<World, SARRectangularMultiblockControllerBase> controllerCreator) {
		super(validPositions, controllerClass, controllerCreator);
		this.toWrap = toWrap;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new MultiblockTankWrapper(this, toWrap));
		}
		return super.getCapability(capability, facing);
	}

}
