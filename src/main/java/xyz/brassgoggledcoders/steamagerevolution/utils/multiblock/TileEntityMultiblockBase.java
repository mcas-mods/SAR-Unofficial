package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public abstract class TileEntityMultiblockBase<T extends SARRectangularMultiblockControllerBase>
		extends RectangularMultiblockTileEntityBase<T> implements IMultiblockTileInfo {

	private Class<T> controllerClass;
	private Function<World, SARRectangularMultiblockControllerBase> controllerCreator;
	protected boolean[] validPositions;
	protected String tankToWrap, inventoryToWrap;

	public TileEntityMultiblockBase(Class<T> controllerClass,
			Function<World, SARRectangularMultiblockControllerBase> controllerCreator) {
		this.controllerClass = controllerClass;
		this.controllerCreator = controllerCreator;
		BlockMultiblockBase block = (BlockMultiblockBase) this.getBlockType();
		if(block != null) {
			if(block.inventoryToWrap != null)
				inventoryToWrap = block.inventoryToWrap;
			if(block.tankToWrap != null)
				tankToWrap = block.tankToWrap;

			validPositions = block.validPositions;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		tankToWrap = data.getString("tankToWrap");
		inventoryToWrap = data.getString("inventoryToWrap");
		validPositions = new boolean[5];
		for(int i = 0; i < 5; i++) {
			validPositions[i] = data.getBoolean("p" + i);
		}
		super.readFromNBT(data);
	}

	@Override
	@Nonnull
	public NBTTagCompound writeToNBT(NBTTagCompound data) {
		if(inventoryToWrap != null)
			data.setString("inventoryToWrap", inventoryToWrap);
		if(tankToWrap != null)
			data.setString("tankToWrap", tankToWrap);
		for(int i = 0; i < 5; i++) {
			data.setBoolean("p" + i, validPositions[i]);
		}
		return super.writeToNBT(data);
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && tankToWrap != null
				|| capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventoryToWrap != null;
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new MultiblockTankWrapper(this, tankToWrap));
		}
		else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
					.cast(new MultiblockInventoryWrapper(this, inventoryToWrap));
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
		return validPositions;
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return validPositions[0];
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return validPositions[1];
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return validPositions[2];
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return validPositions[3];
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return validPositions[4];
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
