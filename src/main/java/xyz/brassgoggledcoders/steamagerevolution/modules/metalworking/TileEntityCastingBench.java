package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking;

import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.smelting.multiblock.crucible.CrucibleRecipe;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;

public class TileEntityCastingBench extends TileEntityBase implements ITickable, ISmartTankCallback {

	// Same as TiCon
	public static final int VALUE_INGOT = 144;
	public static final int VALUE_NUGGET = VALUE_INGOT / 9;
	public static final int VALUE_BLOCK = VALUE_INGOT * 9;
	// public static final int VALUE_ORE = VALUE_INGOT * 2; // TODO Config

	protected ItemStackHandler internal = new ItemStackHandler();
	public FluidTankSmart tank = new FluidTankSmart(VALUE_BLOCK, this);
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
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(internal);
		return super.getCapability(capability, facing);
	}

	public static int getValueFromName(String name) {
		switch(name) {
			case "ingot":
				return VALUE_INGOT;
			case "block":
				return VALUE_BLOCK;
			case "nugget":
				return VALUE_NUGGET;
			case "ore":
				return VALUE_INGOT;
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
		if(this.getWorld().isRemote)
			return;

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
	public void updateFluid(FluidStack fluid) {
		this.tank.setFluid(fluid);
	}

	public static boolean meltMetal(ItemStackHandler source, FluidTank destination) {
		for(int i = 0; i < source.getSlots(); i++) {
			ItemStack stack = source.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(CrucibleRecipe.getMoltenFromSolid(stack) != null) {
					FluidStack molten = new FluidStack(CrucibleRecipe.getMoltenFromSolid(stack), VALUE_BLOCK);
					if(destination.fill(molten, false) == VALUE_BLOCK) {
						destination.fill(molten, true);
						stack.shrink(1);
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean solidifyMetal(FluidTank source, ItemStackHandler destination) {
		if(source.getFluid() != null && source.getFluidAmount() >= VALUE_BLOCK) {
			ItemStack solid = CrucibleRecipe.getSolidFromMolten(source.getFluid().getFluid());
			if(!solid.isEmpty()) {
				if(ItemHandlerHelper.insertItem(destination, solid, true) == ItemStack.EMPTY) {
					ItemHandlerHelper.insertItem(destination, solid, false);
					source.drain(VALUE_BLOCK, true);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		this.markDirty();
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(new PacketFluidUpdate(getPos(), tank.getFluid()),
				getPos(), getWorld().provider.getDimension());
	}
}
