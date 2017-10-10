package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.IOnSlotChanged;
import com.teamacronymcoders.base.tileentities.TileEntityInventoryBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;

public class TileEntityFluidIO extends TileEntityInventoryBase
		implements IHasGui, IOnSlotChanged, ITickable, ISmartTankCallback {

	public FluidTank buffer = new FluidTankSmart(Fluid.BUCKET_VOLUME * 10, this);
	private int fluidTransferRate = 20;

	public TileEntityFluidIO() {
		super(2);
	}

	@Override
	public void update() {
		IItemHandler handler = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if(!handler.getStackInSlot(0).isEmpty()) {
			IFluidHandler itemFluid =
					handler.getStackInSlot(0).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(buffer.fill(itemFluid.drain(fluidTransferRate, false), false) == fluidTransferRate) {
				buffer.fill(itemFluid.drain(fluidTransferRate, true), true);
				this.markDirty();
				this.sendBlockUpdate();
			}
		}
		if(!handler.getStackInSlot(1).isEmpty()) {
			IFluidHandler itemFluid =
					handler.getStackInSlot(1).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(itemFluid.fill(buffer.drain(fluidTransferRate, false), false) == fluidTransferRate) {
				itemFluid.fill(buffer.drain(fluidTransferRate, true), true);
				this.markDirty();
				this.sendBlockUpdate();
			}
		}
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		buffer.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		return buffer.writeToNBT(tag);
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
	public void onSlotChanged(Slot slot) {}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiFluidIO(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerFluidIO(entityPlayer, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateFluid(PacketFluidUpdate message) {
		this.buffer.setFluid(message.fluid);
	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		this.markDirty();
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(new PacketFluidUpdate(getPos(), tank.getFluid()),
				getPos(), getWorld().provider.getDimension());
	}

}
