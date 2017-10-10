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
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.CrucibleRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.CastingBlockRecipe;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;

public class TileEntityCastingBench extends TileEntityBase implements ITickable, ISmartTankCallback {

	protected ItemStackHandler internal = new ItemStackHandler();
	public FluidTankSmart tank = new FluidTankSmart(ModuleMetalworking.VALUE_BLOCK, this);
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
	public void updateFluid(PacketFluidUpdate message) {
		this.tank.setFluid(message.fluid);
	}

	public static boolean meltMetal(ItemStackHandler source, FluidTank destination) {
		for(int i = 0; i < source.getSlots(); i++) {
			ItemStack stack = source.getStackInSlot(i);
			if(!stack.isEmpty()) {
				CrucibleRecipe r = CrucibleRecipe.getRecipe(stack);
				if(r != null) {
					FluidStack molten = r.output;
					if(destination.fill(molten, false) == molten.amount) {
						destination.fill(molten, true);
						stack.shrink(r.input.getCount());
						return true;
					}
				}
			}
		}
		return false;
	}

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
	public void onTankContentsChanged(FluidTank tank) {
		this.markDirty();
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(new PacketFluidUpdate(getPos(), tank.getFluid()),
				getPos(), getWorld().provider.getDimension());
	}
}
