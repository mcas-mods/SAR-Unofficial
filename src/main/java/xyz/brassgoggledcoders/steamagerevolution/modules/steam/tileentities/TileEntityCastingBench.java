package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import org.apache.commons.lang3.StringUtils;

import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;

public class TileEntityCastingBench extends TileEntityBase implements ITickable {

	// Same as TiCon
	public static final int VALUE_INGOT = 144;
	public static final int VALUE_NUGGET = VALUE_INGOT / 9;
	public static final int VALUE_BLOCK = VALUE_INGOT * 9;
	// public static final int VALUE_ORE = VALUE_INGOT * 2; // TODO Config

	protected ItemStackHandler internal = new ItemStackHandler();
	public FluidTank tank = new FluidTank(VALUE_BLOCK);
	public int stateChangeTime = 2400;
	int lastFluidValue = -1;

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
		// Sync for rendering
		if(this.tank.getFluidAmount() != lastFluidValue) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(getPos(), tank.getFluid()), getPos(), getWorld().provider.getDimension());
			lastFluidValue = this.tank.getFluidAmount();
		}
		if(stateChangeTime == 0) {
			// Melting Logic TODO Cache this check
			if(getWorld().getBlockState(getPos().down()).getMaterial() == Material.LAVA) {
				if(meltMetal(internal, tank)) {
					stateChangeTime = 2400;
				}
			}
			// Cooling Logic
			else {
				if(solidifyMetal(tank, internal)) {
					stateChangeTime = 2400;
				}
			}
		}
		else {
			stateChangeTime--;
		}
	}

	@SideOnly(Side.CLIENT)
	public void updateFluid(FluidStack fluid) {
		this.tank.setFluid(fluid);
	}

	public static boolean meltMetal(ItemStackHandler source, FluidTank destination) {
		for(int i = 0; i < source.getSlots(); i++) {
			ItemStack stack = source.getStackInSlot(0);
			if(!stack.isEmpty()) {
				String[] splitName = null;
				// TODO Caching. This *should* never change at runtime.
				for(int oreId : OreDictionary.getOreIDs(stack)) {
					splitName = OreDictionary.getOreName(oreId).split("(?=[A-Z])");
					if(splitName.length != 2)
						return false;
					if(FluidRegistry.isFluidRegistered(splitName[1].toLowerCase())) {
						break;
					}
				}
				if(splitName != null) {
					Fluid fluid = FluidRegistry.getFluid(splitName[1].toLowerCase());
					int value = getValueFromName(splitName[0].toLowerCase()) * stack.getCount();
					if(value != 0) {
						FluidStack toInsert = new FluidStack(fluid, value);
						if(destination.fill(toInsert, false) == value) {
							destination.fill(toInsert, true);
							stack.shrink(1);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static boolean solidifyMetal(FluidTank source, ItemStackHandler destination) {
		if(source.getFluid() != null && source.getFluidAmount() >= VALUE_BLOCK) {
			String oreName = "block" + StringUtils.capitalize(FluidRegistry.getFluidName(source.getFluid()));
			if(OreDictionary.doesOreNameExist(oreName)) {
				source.drain(VALUE_BLOCK, true);
				ItemStack toInsert = OreDictionary.getOres(oreName).get(0);
				toInsert.setCount(1);
				ItemHandlerHelper.insertItemStacked(destination, toInsert, false);
				return true;
			}
		}
		return false;
	}
}
