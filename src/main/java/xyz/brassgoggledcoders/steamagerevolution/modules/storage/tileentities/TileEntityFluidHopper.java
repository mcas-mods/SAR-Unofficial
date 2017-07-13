package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;
import com.teamacronymcoders.base.util.PositionUtils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks.BlockFluidHopper;

public class TileEntityFluidHopper extends TileEntitySlowTick {

	public FluidTank buffer = new FluidTank(Fluid.BUCKET_VOLUME);
	public boolean hasFrom = false;
	public BlockPos toPos = null;

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(buffer);
		return super.getCapability(capability, facing);
	}

	@Override
	public void updateTile() {
		if(world.isRemote)
			return;
		if(BlockFluidHopper.isEnabled(this.getBlockMetadata()) && hasFrom && toPos != null) {
			IFluidHandler from = getWorld().getTileEntity(getPos().up())
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN);
			IFluidHandler to =
					getWorld().getTileEntity(toPos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
							PositionUtils.getFacingFromPositions(getPos(), toPos));
			FluidUtil.tryFluidTransfer(buffer, from, Fluid.BUCKET_VOLUME, true);
			FluidUtil.tryFluidTransfer(to, from, Fluid.BUCKET_VOLUME, true);
		}
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		buffer.readFromNBT(tag);
		hasFrom = tag.getBoolean("from");
		toPos = BlockPos.fromLong(tag.getLong("to"));
		super.readFromDisk(tag);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		buffer.writeToNBT(tag);
		tag.setBoolean("from", hasFrom);
		if(toPos != null)
			tag.setLong("to", toPos.toLong());
		return super.writeToDisk(tag);
	}

}
