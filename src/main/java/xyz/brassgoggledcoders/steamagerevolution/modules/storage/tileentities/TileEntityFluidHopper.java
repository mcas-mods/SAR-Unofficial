package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;
import com.teamacronymcoders.base.util.PositionUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks.BlockFluidHopper;

public class TileEntityFluidHopper extends TileEntitySlowTick {

	private FluidTank buffer = new FluidTank(Fluid.BUCKET_VOLUME);
	private boolean hasFrom = false;
	private BlockPos toPos = null;
	private boolean hasCache = false;

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
		if(!hasCache)
			recalculateCache(getWorld(), getPos(), this.getWorld().getBlockState(getPos()), null);

		if(BlockFluidHopper.isEnabled(this.getBlockMetadata())) {
			if(toPos != null) {
				IFluidHandler to =
						getWorld().getTileEntity(toPos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
								PositionUtils.getFacingFromPositions(getPos(), toPos));
				FluidUtil.tryFluidTransfer(to, buffer, Fluid.BUCKET_VOLUME, true);
			}
			if(hasFrom) {
				IFluidHandler from = getWorld().getTileEntity(getPos().up())
						.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN);

				FluidUtil.tryFluidTransfer(buffer, from, Fluid.BUCKET_VOLUME, true);
			}
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

	public void recalculateCache(World worldIn, BlockPos pos, IBlockState state, BlockPos fromPos) {
		hasCache = true;

		boolean flag = !worldIn.isBlockPowered(pos);

		if(flag != ((Boolean) state.getValue(BlockFluidHopper.ENABLED)).booleanValue()) {
			worldIn.setBlockState(pos, state.withProperty(BlockFluidHopper.ENABLED, Boolean.valueOf(flag)), 4);
		}

		if(fromPos == null) {
			fromPos = pos.offset(state.getValue(BlockFluidHopper.FACING));
		}
		EnumFacing facing = PositionUtils.getFacingFromPositions(pos, fromPos);
		if(facing == EnumFacing.DOWN) {
			TileEntity up = worldIn.getTileEntity(pos.up());
			if(up != null && up.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
				hasFrom = true;
			}
			else {
				hasFrom = false;
			}
		}
		else if(facing == state.getValue(BlockFluidHopper.FACING).getOpposite()) {
			TileEntity pointed = worldIn.getTileEntity(fromPos);
			if(pointed != null && pointed.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
				toPos = fromPos;
			}
			else {
				toPos = null;
			}
		}
	}
}
