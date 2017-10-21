package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityMultiblockItemWrapper<T extends SARRectangularMultiblockControllerBase>
		extends TileEntityMultiblockBase<T> {
	String toWrap;

	public TileEntityMultiblockItemWrapper() {}

	public TileEntityMultiblockItemWrapper(String toWrap, boolean[] validPositions, Class<T> controllerClass,
			Function<World, SARRectangularMultiblockControllerBase> controllerCreator) {
		super(validPositions, controllerClass, controllerCreator);
		this.toWrap = toWrap;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new MultiblockInventoryWrapper(this, toWrap));
		}
		return super.getCapability(capability, facing);
	}

}
