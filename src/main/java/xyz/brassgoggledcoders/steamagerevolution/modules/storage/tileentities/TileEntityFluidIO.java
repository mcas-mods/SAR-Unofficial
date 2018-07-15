package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityInventoryBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityFluidIO extends TileEntityInventoryBase implements IHasGui, ITickable {

	public FluidTank buffer;// TODO = new FluidTankSmart(Fluid.BUCKET_VOLUME * 10, this,
							// TankType.UNDEFINED);
	private int fluidTransferRate = 20;

	public TileEntityFluidIO() {
		super(2);
	}

	@Override
	public void update() {
		IItemHandler handler = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if(!handler.getStackInSlot(0).isEmpty()) {
			IFluidHandler itemFluid = handler.getStackInSlot(0)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(buffer.fill(itemFluid.drain(fluidTransferRate, false), false) == fluidTransferRate) {
				buffer.fill(itemFluid.drain(fluidTransferRate, true), true);
				markDirty();
				sendBlockUpdate();
			}
		}
		if(!handler.getStackInSlot(1).isEmpty()) {
			IFluidHandler itemFluid = handler.getStackInSlot(1)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(itemFluid.fill(buffer.drain(fluidTransferRate, false), false) == fluidTransferRate) {
				itemFluid.fill(buffer.drain(fluidTransferRate, true), true);
				markDirty();
				sendBlockUpdate();
			}
		}
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		// buffer.readFromNBT(tag);
		super.readFromDisk(tag);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		// buffer.writeToNBT(tag);
		return super.writeToDisk(tag);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) buffer;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiFluidIO(entityPlayer, (TileEntityFluidIO) world.getTileEntity(blockPos));
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerFluidIO(entityPlayer, (TileEntityFluidIO) world.getTileEntity(blockPos));
	}

	// @Override
	// @SideOnly(Side.CLIENT)
	// public void updateFluid(PacketFluidUpdate message) {
	// buffer.setFluid(message.fluid);
	// }
	//
	// @Override
	// public void onTankContentsChanged(FluidTankSmart tank) {
	// SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
	// new PacketFluidUpdate(getPos(), tank.getFluid(),
	// TankType.getNetworkID(tank.getTankType())), getPos(),
	// getWorld().provider.getDimension());
	// }

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setTag("tank", buffer.writeToNBT(new NBTTagCompound()));
		return new SPacketUpdateTileEntity(pos, 3, nbttagcompound);
	}

	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
		nbt.setTag("tank", buffer.writeToNBT(new NBTTagCompound()));
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		buffer.readFromNBT(pkt.getNbtCompound().getCompoundTag("tank"));
	}

}
