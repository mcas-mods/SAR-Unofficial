package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler;

import java.util.LinkedHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.boilerplate.api.IDebuggable;
import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;

public class TileEntityWaterInput extends TileEntityBasicBoilerPart implements IDebuggable {

	public FluidTank buffer = new WaterLockedTank(Fluid.BUCKET_VOLUME);

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		buffer.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag = super.writeToNBT(tag);
		buffer.writeToNBT(tag);
		return tag;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) buffer;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return false;
	}

	public static class WaterLockedTank extends FluidTank {
		public WaterLockedTank(int capacity) {
			super(capacity);
		}

		@Override
		public boolean canFillFluidType(FluidStack fluid) {
			if(fluid.getFluid() == FluidRegistry.WATER)
				return canFill();
			else
				return false;
		}
	}

	@Override
	public LinkedHashMap<String, String> getDebugStrings(LinkedHashMap<String, String> debugStrings) {
		debugStrings.put("fluidAmount", "" + buffer.getFluidAmount());
		return debugStrings;
	}

	@Override
	public String getPartName() {
		return "Water Input";
	}
}
