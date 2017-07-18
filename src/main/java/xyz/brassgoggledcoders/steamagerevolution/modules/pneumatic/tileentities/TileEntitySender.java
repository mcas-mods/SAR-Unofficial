package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;

import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.ModulePneumatic;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockPneumaticTube;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockSender;

public class TileEntitySender extends TileEntitySlowTick {

	public static int maxDistance = 16;
	private static int rate = 1;

	private boolean hasCache = false;
	private EnumFacing facing = EnumFacing.NORTH;
	private IItemHandler sendInventory = null;
	private IItemHandler recieveInventory = null;
	private BlockPos[] tubePositions = null;

	@Override
	public void updateTile() {
		if(this.getWorld().isRemote)
			return;

		if(!hasCache)
			recalculateCache(getWorld(), getPos());

		if(sendInventory != null && recieveInventory != null) {
			// TODO Rewrite inventory handling
			if(recieveInventory.getStackInSlot(0).isEmpty() || recieveInventory.getStackInSlot(0)
					.getCount() < recieveInventory.getStackInSlot(0).getMaxStackSize()) {
				if(recieveInventory.insertItem(0, sendInventory.getStackInSlot(0).splitStack(rate),
						false) == ItemStack.EMPTY) {
					this.getWorld().playSound(null, this.getPos(), SoundEvents.ENTITY_CAT_HISS, SoundCategory.BLOCKS, 1,
							1);
					for(int i2 = 0; i2 < maxDistance; i2++) {
						if(tubePositions[i2] != null) {
							SteamAgeRevolution.proxy.spawnSmoke(this.getPos());
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
		facing = worldIn.getBlockState(pos).getValue(BlockSender.FACING);
		TileEntity behind = this.getWorld().getTileEntity(pos.offset(facing.getOpposite()));
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
			if(block == ModulePneumatic.router) {
				recieveInventory = this.getWorld().getTileEntity(getPos().offset(facing, i))
						.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
				return;
			}
			else if(block == ModulePneumatic.pneumaticTube && this.getWorld().getBlockState(currentPos)
					.getValue(BlockPneumaticTube.AXIS) == facing.getAxis()) {
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
}
