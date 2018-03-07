package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.GuiSingleTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.ISmartTankCallback;

public class TileEntityBasicFluidTank extends TileEntityBase implements ISmartTankCallback, IHasGui {
	public FluidTank tank;

	public TileEntityBasicFluidTank() {
		tank = new FluidTankSmart(Fluid.BUCKET_VOLUME * 16, this);
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
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		return tank.writeToNBT(tag);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateFluid(PacketFluidUpdate message) {
		this.tank.setFluid(message.fluid);
	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		this.markDirty();
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(new PacketFluidUpdate(getPos(), tank.getFluid()),
				getPos(), getWorld().provider.getDimension());
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiSingleTank(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return null;
	}
}
