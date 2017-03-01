package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.IOnSlotChanged;
import com.teamacronymcoders.base.tileentities.TileEntityInventoryBase;
import com.teamacronymcoders.base.util.ItemStackUtils;

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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityFluidIO extends TileEntityInventoryBase implements IHasGui, IOnSlotChanged, ITickable {

	public FluidTank buffer = new FluidTank(Fluid.BUCKET_VOLUME * 10);
	private int fluidTransferRate = 20;

	public TileEntityFluidIO() {
		super(2);
	}

	@Override
	public void update() {
		IItemHandler handler = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if(ItemStackUtils.isItemNonNull(handler.getStackInSlot(0))) {
			IFluidHandler itemFluid =
					handler.getStackInSlot(0).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			if(buffer.fill(itemFluid.drain(fluidTransferRate, false), false) == fluidTransferRate) {
				buffer.fill(itemFluid.drain(fluidTransferRate, true), true);
				this.markDirty();
				this.sendBlockUpdate();
			}
		}
		if(ItemStackUtils.isItemNonNull(handler.getStackInSlot(1))) {
			IFluidHandler itemFluid =
					handler.getStackInSlot(1).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
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
		super.readFromDisk(tag);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		buffer.writeToNBT(tag);
		return super.writeToDisk(tag);
	}

	@Override
	protected void readFromUpdatePacket(NBTTagCompound data) {
		if(!data.getString("fluid").isEmpty())
			this.buffer.setFluid(
					new FluidStack(FluidRegistry.getFluid(data.getString("fluid")), data.getInteger("level")));
		super.readFromUpdatePacket(data);
	};

	@Override
	protected NBTTagCompound writeToUpdatePacket(NBTTagCompound data) {
		if(buffer.getFluid() != null) {
			data.setString("fluid", FluidRegistry.getFluidName(buffer.getFluid().getFluid()));
			data.setInteger("level", this.buffer.getFluidAmount());
		}
		return super.writeToUpdatePacket(data);
	};

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

}
