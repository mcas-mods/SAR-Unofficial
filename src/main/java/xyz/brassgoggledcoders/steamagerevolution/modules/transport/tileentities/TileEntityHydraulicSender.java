package xyz.brassgoggledcoders.steamagerevolution.modules.transport.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.ModulePneumatic;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.blocks.BlockHydraulicSender;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.blocks.BlockHydraulicTube;

public class TileEntityHydraulicSender extends TileEntitySlowTick {

	public static int maxDistance = TileEntityPneumaticSender.maxDistance * 2;
	private static int rate = Fluid.BUCKET_VOLUME;

	private boolean hasCache = false;
	private EnumFacing facing = EnumFacing.NORTH;
	private IFluidHandler sendInventory = null;
	private IFluidHandler recieveInventory = null;
	private BlockPos[] tubePositions = null;

	@Override
	public void updateTile() {
		if(this.getWorld().isRemote)
			return;

		if(!hasCache)
			recalculateCache(getWorld(), getPos());

		if(sendInventory != null && recieveInventory != null) {
			FluidStack toTransfer = FluidUtil.tryFluidTransfer(recieveInventory, sendInventory, rate, false);
			if(toTransfer != null && toTransfer.amount == rate) {
				FluidUtil.tryFluidTransfer(recieveInventory, sendInventory, rate, true);
			}
		}
	}

	public void recalculateCache(World worldIn, BlockPos pos) {
		hasCache = true;
		SteamAgeRevolution.instance.getLogger().devInfo("Recalc Cache");
		facing = worldIn.getBlockState(pos).getValue(BlockHydraulicSender.FACING);
		TileEntity behind = this.getWorld().getTileEntity(pos.offset(facing.getOpposite()));
		if(behind != null
				&& behind.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite())) {
			sendInventory = behind.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
		}
		else {
			sendInventory = null;
		}
		tubePositions = new BlockPos[maxDistance];
		for(int i = 1; i < maxDistance; i++) {
			BlockPos currentPos = pos.offset(facing, i);
			Block block = world.getBlockState(currentPos).getBlock();
			if(block == ModulePneumatic.hydraulicRouter) {
				recieveInventory = this.getWorld().getTileEntity(getPos().offset(facing, i))
						.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
				return;
			}
			else if(block == ModulePneumatic.hydraulicTube && this.getWorld().getBlockState(currentPos)
					.getValue(BlockHydraulicTube.AXIS) == facing.getAxis()) {
				tubePositions[i] = currentPos;
				continue;
			}
			else {
				recieveInventory = null;
				tubePositions = null;
				return;
			}
		}
	}

	@Override
	protected void readFromDisk(NBTTagCompound data) {}

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		return data;
	}
}
