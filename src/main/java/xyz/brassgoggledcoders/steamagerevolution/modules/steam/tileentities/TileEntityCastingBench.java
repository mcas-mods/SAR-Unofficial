package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import org.apache.commons.lang3.StringUtils;

import com.teamacronymcoders.base.tileentities.TileEntityBase;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityCastingBench extends TileEntityBase implements ITickable {

	// Same as TiCon
	public static final int VALUE_INGOT = 144;
	public static final int VALUE_NUGGET = VALUE_INGOT / 9;
	public static final int VALUE_BLOCK = VALUE_INGOT * 9;
	public static final int VALUE_ORE = VALUE_INGOT * 2; // TODO Config

	protected ItemStackHandler internal = new ItemStackHandler();
	public FluidTank tank = new FluidTank(VALUE_BLOCK);
	public int stateChangeTime = 2400;
	int lastFluidValue = -1;

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(facing == EnumFacing.DOWN) {
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

	public int getValueFromName(String name) {
		switch(name) {
			case "ingot":
				return VALUE_INGOT;
			case "block":
				return VALUE_BLOCK;
			case "nugget":
				return VALUE_NUGGET;
			case "ore":
				return VALUE_ORE;
			default:
				return 0;
		}
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		tag.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		tag.setTag("inv", internal.serializeNBT());
		tag.setInteger("coolTime", stateChangeTime);
		return super.writeToDisk(tag);
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		tank.readFromNBT(tag.getCompoundTag("tank"));
		internal.deserializeNBT(tag.getCompoundTag("inv"));
		stateChangeTime = tag.getInteger("cooltime");
		super.readFromDisk(tag);
	}

	@Override
	public NBTTagCompound writeToUpdatePacket(NBTTagCompound tag) {
		tank.writeToNBT(tag);
		return super.writeToUpdatePacket(tag);
	}

	@Override
	public void readFromUpdatePacket(NBTTagCompound tag) {
		tank.readFromNBT(tag);
		super.readFromUpdatePacket(tag);
	}

	@Override
	public void update() {
		if(worldObj.isRemote)
			return;
		// Sync for rendering
		if(this.tank.getFluidAmount() != lastFluidValue) {
			this.sendBlockUpdate();
			lastFluidValue = this.tank.getFluidAmount();
		}

		ItemStack stack = this.internal.getStackInSlot(0);

		// Melting Logic TODO Cache this check
		if(getWorld().getBlockState(getPos().down()).getMaterial() == Material.LAVA) {
			if(ItemStackUtils.isItemNonNull(stack)) {
				for(int oreId : OreDictionary.getOreIDs(stack)) {
					String[] splitName = OreDictionary.getOreName(oreId).split("(?=[A-Z])");
					if(splitName.length != 2)
						return;
					if(FluidRegistry.isFluidRegistered(splitName[1].toLowerCase())) {
						Fluid fluid = FluidRegistry.getFluid(splitName[1].toLowerCase());
						int value = getValueFromName(splitName[0].toLowerCase()) * stack.stackSize;
						if(value != 0) {
							FluidStack toInsert = new FluidStack(fluid, value);
							if(tank.fill(toInsert, false) == value) {
								tank.fill(toInsert, true);
								stack.stackSize--;
								this.internal.setStackInSlot(0, stack);
							}
						}
					}
				}
			}
		}
		// Cooling Logic
		else {
			if(this.tank.getFluid() != null && this.tank.drain(VALUE_BLOCK, false).amount == VALUE_BLOCK
					&& !ItemStackUtils.isItemNonNull(stack)) {
				String oreName = "block" + StringUtils.capitalize(FluidRegistry.getFluidName(this.tank.getFluid()));
				if(OreDictionary.doesOreNameExist(oreName)) {
					if(stateChangeTime == 0) {
						this.tank.drain(VALUE_BLOCK, true);
						ItemStack toInsert = OreDictionary.getOres(oreName).get(0);
						toInsert.stackSize = 1;
						this.internal.insertItem(0, toInsert, false);
						stateChangeTime = 2400;
					}
					else {
						stateChangeTime--;
					}
				}
			}
		}
	}

}
