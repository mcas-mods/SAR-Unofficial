package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.FluidTankSingleType;

public class TileEntityFurnaceHeater extends TileEntityBase implements ITickable {

	public FluidTank steamTank = new FluidTankSingleType(Fluid.BUCKET_VOLUME * 4, "steam");

	public static final int fluidUsePerOperation = Fluid.BUCKET_VOLUME / 10;

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
				|| super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(steamTank);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void update() {
		if(this.steamTank.getFluidAmount() >= fluidUsePerOperation) {
			if(this.getWorld().getTileEntity(getPos().up()) instanceof TileEntityFurnace) {
				TileEntityFurnace furnace = (TileEntityFurnace) this.getWorld().getTileEntity(getPos().up());
				furnace.getTileData().setInteger("BurnTime", 20);
				furnace.readFromNBT(new NBTTagCompound());
				steamTank.drain(fluidUsePerOperation, true);
				// furnace.markDirty();
				// furnace.smeltItem();
			}
		}
	}
}
