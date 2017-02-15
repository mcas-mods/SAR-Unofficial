package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityBasicFluidTank extends TileEntityBase implements ITickable {
	public FluidTank tank = new FluidTank(Fluid.BUCKET_VOLUME * 16);
	int lastFluidLevel = -1;

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		tank.readFromNBT(tag);
		super.readFromDisk(tag);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		tank.writeToNBT(tag);
		return super.writeToDisk(tag);
	}

	@Override
	public void readFromUpdatePacket(NBTTagCompound tag) {
		tank.readFromNBT(tag);
		super.readFromUpdatePacket(tag);
	}

	@Override
	public NBTTagCompound writeToUpdatePacket(NBTTagCompound tag) {
		tank.writeToNBT(tag);
		return super.writeToUpdatePacket(tag);
	}

	@Override
	public void update() {
		if(getWorld().isRemote)
			return;
		if(lastFluidLevel != this.tank.getFluidAmount()) {
			this.sendBlockUpdate();
			this.lastFluidLevel = this.tank.getFluidAmount();
		}

	}
}
