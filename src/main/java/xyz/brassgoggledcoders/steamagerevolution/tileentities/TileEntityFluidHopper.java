package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import com.teamacronymcoders.base.util.PositionUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.blocks.BlockFluidHopper;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.TileEntityInventory;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMachine;

//TODO Switch to inventory system
public class TileEntityFluidHopper extends TileEntityInventory<InventoryBasic> implements ITickable {

	static final String uid = "fluid_hopper";
	private boolean hasFrom = false;
	private BlockPos toPos = null;
	private boolean hasCache = false;

	static {
		IMachine.referenceMachinesList.put(uid, new TileEntityFluidHopper());
	}

	public TileEntityFluidHopper() {
		this.setInventory(new InventoryBasic(this).addFluidPiece("tank", 78, 11, Fluid.BUCKET_VOLUME));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
					.cast(this.getInventory().getFluidPiece("tank").getHandler());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void update() {
		if(world.isRemote) {
			return;
		}

		if(!hasCache) {
			recalculateCache(getWorld(), getPos(), getWorld().getBlockState(getPos()), null);
		}

		if(BlockFluidHopper.isEnabled(getBlockMetadata())) {
			if(toPos != null) {
				IFluidHandler to = getWorld().getTileEntity(toPos).getCapability(
						CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
						PositionUtils.getFacingFromPositions(getPos(), toPos));
				if(to != null) { // TODO This should not happen
					FluidUtil.tryFluidTransfer(to, this.getInventory().getFluidPiece("tank").getHandler(),
							Fluid.BUCKET_VOLUME, true);
				}
			}
			if(hasFrom) {
				IFluidHandler from = getWorld().getTileEntity(getPos().up())
						.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN);
				if(from != null) { // TODO Neither should this
					FluidUtil.tryFluidTransfer(this.getInventory().getFluidPiece("tank").getHandler(), from,
							Fluid.BUCKET_VOLUME, true);
				}
			}
		}
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		hasFrom = tag.getBoolean("from");
		toPos = BlockPos.fromLong(tag.getLong("to"));
		super.readFromDisk(tag);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		tag.setBoolean("from", hasFrom);
		if(toPos != null) {
			tag.setLong("to", toPos.toLong());
		}
		return super.writeToDisk(tag);
	}

	public void recalculateCache(World worldIn, BlockPos pos, IBlockState state, BlockPos fromPos) {
		hasCache = true;
		boolean flag = !worldIn.isBlockPowered(pos);

		if(flag != state.getValue(BlockFluidHopper.ENABLED).booleanValue()) {
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

	@Override
	public String getUID() {
		return uid;
	}
}
