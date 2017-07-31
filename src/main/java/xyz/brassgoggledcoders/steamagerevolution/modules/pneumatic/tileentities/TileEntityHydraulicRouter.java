package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockHydraulicRouter;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockPneumaticRouter;

public class TileEntityHydraulicRouter extends TileEntitySlowTick implements ITickable {

	boolean hasCache = false;
	private FluidTank buffer = new FluidTank(Fluid.BUCKET_VOLUME * 3);
	private IFluidHandler otherHandler;
	private static int fluidTransferPerUpdate = Fluid.BUCKET_VOLUME / 10;

	private int tickRate = 20; // Default to once a second
	private int ticks = 0;

	public TileEntityHydraulicRouter() {
		super(1);
	}

	@Override
	protected void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public void updateTile() {
		if(world.isRemote)
			return;
		if(!hasCache)
			recalculateCache(getWorld(), getPos(), getWorld().getBlockState(getPos()), null);

		if(otherHandler != null) {
			FluidStack toTransfer = FluidUtil.tryFluidTransfer(otherHandler, buffer, fluidTransferPerUpdate, false);
			if(toTransfer != null && toTransfer.amount == fluidTransferPerUpdate) {
				FluidUtil.tryFluidTransfer(otherHandler, buffer, fluidTransferPerUpdate, true);
			}
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(buffer);
		return super.getCapability(capability, facing);
	}

	public void recalculateCache(World worldIn, BlockPos pos, IBlockState state, BlockPos fromPos) {
		if(fromPos == null) {
			fromPos = pos.offset(state.getValue(BlockPneumaticRouter.FACING));
		}
		TileEntity other = getWorld().getTileEntity(fromPos);
		if(other != null && other.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
				state.getValue(BlockHydraulicRouter.FACING).getOpposite())) {
			otherHandler = other.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
					state.getValue(BlockHydraulicRouter.FACING).getOpposite());
		}
	}

}
