package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityCrucible extends TileEntityBase {

	// Same as TiCon
	public static final int VALUE_INGOT = 144;
	public static final int VALUE_NUGGET = VALUE_INGOT / 9;
	public static final int VALUE_BLOCK = VALUE_INGOT * 9;
	public static final int VALUE_ORE = VALUE_INGOT * 2; // TODO Config

	// protected ItemStackHandler internal = new ItemStackHandler();
	protected FluidTank tank = new FluidTank(Fluid.BUCKET_VOLUME * 16);

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
				|| super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		return super.getCapability(capability, facing);
	}

	// Fired from the block.
	public void onEntityCollided(Entity entityIn) {
		if((getWorld().getBlockState(getPos().down()).getMaterial() != Material.LAVA))
			return;

		if(entityIn instanceof EntityItem) {
			ItemStack stack = ((EntityItem) entityIn).getEntityItem();
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
							entityIn.setDead();
						}
					}
				}
			}
		}
		else {
			entityIn.setFire(10);
		}
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
		return super.writeToDisk(tag);
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		tank.readFromNBT(tag.getCompoundTag("tank"));
		super.readFromDisk(tag);
	}

}
