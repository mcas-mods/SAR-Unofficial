package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;

public class TileEntityEvaporator extends TileEntityBase implements ISmartTankCallback, ITickable {
	public FluidTank input;
	public FluidTank output;
	public ItemStackHandler fuel;
	int ticks = 0;
	EvaporatorRecipe currentRecipe = null;

	public TileEntityEvaporator() {
		super();
		input = new FluidTankSmart(Fluid.BUCKET_VOLUME * 8, this);
		output = new FluidTankSmart(Fluid.BUCKET_VOLUME * 8, this);
		fuel = new ItemStackHandler();
	}

	@Override
	public void update() {
		if(world.isRemote)
			return;
		if(currentRecipe == null) {
			if(!fuel.getStackInSlot(0).isEmpty() && TileEntityFurnace.isItemFuel(fuel.getStackInSlot(0))) {
				if(input.getFluidAmount() > 0 && EvaporatorRecipe.getRecipe(input.getFluid()) != null) {
					currentRecipe = EvaporatorRecipe.getRecipe(input.getFluid());
					return;
				}
			}
		}
		else {
			if(ticks == currentRecipe.ticksToProcess) {
				if(output.fill(currentRecipe.output, false) == currentRecipe.output.amount) {
					output.fill(currentRecipe.output, true);
					input.drain(currentRecipe.input, true);
					currentRecipe = null;
				}
			}
			else {
				ticks++;
			}
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(facing == EnumFacing.UP) {
			return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
		}
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(facing == EnumFacing.UP) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(fuel);
		}
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			if(facing == EnumFacing.DOWN) {
				return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(output);
			}
			else {
				return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(input);
			}
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		input.readFromNBT(tag.getCompoundTag("input"));
		output.readFromNBT(tag.getCompoundTag("output"));
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		tag.setTag("input", new NBTTagCompound());
		tag.setTag("output", new NBTTagCompound());
		return tag;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateFluid(PacketFluidUpdate message) {

	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {

	}
}
