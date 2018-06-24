package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.*;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.ISmartTankCallback;

public class TileEntityCastingBench extends TileEntityBase implements ITickable, ISmartTankCallback {

	public static int inputCapacity = ModuleMetalworking.VALUE_BLOCK;
	protected ItemStackHandler internal = new ItemStackHandler();
	public FluidTankSmart tank = new FluidTankSmart(inputCapacity, this);
	public int stateChangeTime = 2400;

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(facing.getAxis() == Axis.Y) {
			return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
					|| super.hasCapability(capability, facing);
		}
		else {
			return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
					|| super.hasCapability(capability, facing);
		}
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		}
		else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(internal);
		}
		return super.getCapability(capability, facing);
	}

	public static int getValueFromName(String name) {
		switch(name) {
			case "ingot":
				return ModuleMetalworking.VALUE_INGOT;
			case "block":
				return ModuleMetalworking.VALUE_BLOCK;
			case "nugget":
				return ModuleMetalworking.VALUE_NUGGET;
			case "ore":
				return ModuleMetalworking.VALUE_INGOT;
			default:
				return 0;
		}
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		tag.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		tag.setTag("inv", internal.serializeNBT());
		tag.setInteger("coolTime", stateChangeTime);
		return tag;
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		tank.readFromNBT(tag.getCompoundTag("tank"));
		internal.deserializeNBT(tag.getCompoundTag("inv"));
		stateChangeTime = tag.getInteger("cooltime");
	}

	@Override
	public void update() {
		if(getWorld().isRemote) {
			return;
		}

		if(solidifyMetal(tank, internal)) {
			if(stateChangeTime == 0) {
				stateChangeTime = 2400;
			}
			else {
				stateChangeTime--;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateFluid(PacketFluidUpdate message) {
		tank.setFluid(message.fluid);
	}

	// public static boolean meltMetal(ItemStackHandler source, FluidTank
	// destination) {
	// for(int i = 0; i < source.getSlots(); i++) {
	// ItemStack stack = source.getStackInSlot(i);
	// if(!stack.isEmpty()) {
	// CrucibleRecipe r = CrucibleRecipe.getRecipe(stack);
	// if(r != null) {
	// FluidStack molten = r.output;
	// if(destination.fill(molten, false) == molten.amount) {
	// destination.fill(molten, true);
	// stack.shrink(r.input.getCount());
	// return true;
	// }
	// }
	// }
	// }
	// return false;
	// }

	public static boolean solidifyMetal(FluidTank source, ItemStackHandler destination) {
		if(source.getFluid() != null && source.getFluidAmount() >= ModuleMetalworking.VALUE_BLOCK) {
			CastingBlockRecipe r = CastingBlockRecipe.getRecipe(source.getFluid());
			ItemStack solid = r.output;
			if(!solid.isEmpty()) {
				// TODO Cleanup
				if(ItemHandlerHelper.insertItem(destination, solid, true) == ItemStack.EMPTY
						&& (source.drain(r.input.amount, false) != null
								&& source.drain(r.input.amount, false).amount == r.input.amount)) {
					ItemHandlerHelper.insertItem(destination, solid, false);
					source.drain(r.input.amount, true);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onTankContentsChanged(FluidTankSmart tank) {
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(new PacketFluidUpdate(getPos(), tank.getFluid()),
				getPos(), getWorld().provider.getDimension());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		return new SPacketUpdateTileEntity(pos, 3, nbttagcompound);
	}

	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
		nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		tank.readFromNBT(pkt.getNbtCompound().getCompoundTag("tank"));
	}
}
