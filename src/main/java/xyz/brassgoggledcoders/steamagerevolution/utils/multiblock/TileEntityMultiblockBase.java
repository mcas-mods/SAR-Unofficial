package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public abstract class TileEntityMultiblockBase<T extends SARRectangularMultiblockControllerBase>
		extends RectangularMultiblockTileEntityBase<T> implements IMultiblockTileInfo {

	private BlockMultiblockBase<T> block;
	private Class<T> controllerClass;
	private Function<World, SARRectangularMultiblockControllerBase> controllerCreator;

	public TileEntityMultiblockBase(Class<T> controllerClass,
			Function<World, SARRectangularMultiblockControllerBase> controllerCreator) {
		this.block = (BlockMultiblockBase<T>) getBlockType();
		this.controllerClass = controllerClass;
		this.controllerCreator = controllerCreator;
	}

	@Override
	protected void setWorldCreate(World worldIn) {
		block = (BlockMultiblockBase<T>) worldIn.getBlockState(pos).getBlock();
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && block.tankToWrap != null
				|| capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && block.inventoryToWrap != null;
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
					.cast(new MultiblockTankWrapper(this, block.tankToWrap));
		}
		else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
					.cast(new MultiblockInventoryWrapper(this, block.inventoryToWrap));
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public Class<T> getMultiblockControllerType() {
		return controllerClass;
	}

	@Override
	public IMultiblockControllerInfo getControllerInfo() {
		return (IMultiblockControllerInfo) controllerCreator.apply(Minecraft.getMinecraft().world);
	}

	@Override
	public boolean[] getValidPositions() {
		return block.validPositions;
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return block.validPositions[0];
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return block.validPositions[1];
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return block.validPositions[2];
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return block.validPositions[3];
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return block.validPositions[4];
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return controllerCreator.apply(getWorld());
	}

	@Override
	public void onMachineActivated() {}

	@Override
	public void onMachineDeactivated() {}

}
