package xyz.brassgoggledcoders.steamagerevolution.modules.transport.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;

import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.ModuleTransport;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.blocks.BlockPneumaticSender;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.blocks.BlockPneumaticTube;

public class TileEntityPneumaticSender extends TileEntitySlowTick {

	public static int maxDistance = 16;
	private static int rate = 1;

	private boolean hasCache = false;
	private EnumFacing facing = EnumFacing.NORTH;
	private IItemHandler sendInventory = null;
	private IItemHandler recieveInventory = null;
	private BlockPos[] tubePositions = null;

	@Override
	public void updateTile() {
		if(getWorld().isRemote) {
			return;
		}

		if(!hasCache) {
			recalculateCache(getWorld(), getPos());
		}

		if(sendInventory != null && recieveInventory != null) {
			// TODO Rewrite inventory handling
			for(int i = 0; i < sendInventory.getSlots(); i++) {
				if(ItemHandlerHelper
						.insertItem(recieveInventory, sendInventory.getStackInSlot(i).copy().splitStack(rate), true)
						.isEmpty()) {
					getWorld().playSound(null, getPos(), SoundEvents.ENTITY_CAT_HISS, SoundCategory.BLOCKS, 1, 1);
					ItemHandlerHelper.insertItem(recieveInventory, sendInventory.getStackInSlot(i).splitStack(rate),
							false);
					for(int i2 = 0; i2 < maxDistance; i2++) {
						if(tubePositions[i2] != null) {
							SteamAgeRevolution.proxy.spawnSmoke(getPos());
							SteamAgeRevolution.proxy.spawnSmoke(tubePositions[i2]);
						}
					}
				}
			}
		}
	}

	public void recalculateCache(World worldIn, BlockPos pos) {
		hasCache = true;
		SteamAgeRevolution.instance.getLogger().devInfo("Recalc Cache");
		facing = worldIn.getBlockState(pos).getValue(BlockPneumaticSender.FACING);
		TileEntity behind = getWorld().getTileEntity(pos.offset(facing.getOpposite()));
		if(behind != null
				&& behind.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
			sendInventory = behind.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
		}
		else {
			sendInventory = null;
		}
		tubePositions = new BlockPos[maxDistance];
		for(int i = 1; i < maxDistance; i++) {
			BlockPos currentPos = pos.offset(facing, i);
			Block block = world.getBlockState(currentPos).getBlock();
			if(block == ModuleTransport.pneumaticRouter) {
				recieveInventory = getWorld().getTileEntity(getPos().offset(facing, i))
						.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
				return;
			}
			else if(block == ModuleTransport.pneumaticTube
					&& getWorld().getBlockState(currentPos).getValue(BlockPneumaticTube.AXIS) == facing.getAxis()) {
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
	protected void readFromDisk(NBTTagCompound data) {
	}

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		return data;
	}
}
