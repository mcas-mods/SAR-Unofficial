package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;

public class TileEntityBasicFluidTank extends TileEntityBase implements ITickable {
	public FluidTank tank;
	int lastFluidLevel;

	public TileEntityBasicFluidTank() {
		this.tank = new FluidTank(Fluid.BUCKET_VOLUME * 16);
		lastFluidLevel = -1;
	}

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
	public void update() {
		if(getWorld().isRemote)
			return;
		if(lastFluidLevel != this.tank.getFluidAmount()) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(getPos(), tank.getFluid()), getPos(), getWorld().provider.getDimension());
			this.lastFluidLevel = this.tank.getFluidAmount();
		}
	}

	@SideOnly(Side.CLIENT)
	public void updateFluid(FluidStack fluid) {
		this.tank.setFluid(fluid);
	}
}
