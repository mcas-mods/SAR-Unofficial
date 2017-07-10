package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.ModulePneumatic;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockPneumaticTube;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockSender;

public class TileEntitySender extends TileEntitySlowTick {

	private int maxDistance = 16;
	private int rate = 1;

	@Override
	public void updateTile() {
		EnumFacing facing = this.getWorld().getBlockState(getPos()).getValue(BlockSender.FACING);
		TileEntity behind = this.getWorld().getTileEntity(getPos().offset(facing.getOpposite()));
		if(behind.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
			IItemHandler sendInventory =
					behind.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
			if(sendInventory.getStackInSlot(0) == null) {
				return;
			}
			BlockPos[] tubePositions = new BlockPos[maxDistance];
			for(int i = 1; i < maxDistance; i++) {
				BlockPos currentPos = getPos().offset(facing, i);
				Block block = this.getWorld().getBlockState(currentPos).getBlock();
				if(block == ModulePneumatic.router) {
					IItemHandler recieveInventory = this.getWorld().getTileEntity(getPos().offset(facing, i))
							.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
					if(recieveInventory.getStackInSlot(0) == null || recieveInventory.getStackInSlot(0)
							.getCount() < recieveInventory.getStackInSlot(0).getMaxStackSize()) {
						// TODO Creates zero-size stacks
						recieveInventory.insertItem(0, sendInventory.getStackInSlot(0).splitStack(rate), false);
						this.getWorld().playSound(null, this.getPos(), SoundEvents.ENTITY_CAT_HISS,
								SoundCategory.BLOCKS, 1, 1);
						for(int i2 = 0; i2 < maxDistance; i2++) {
							if(tubePositions[i2] != null) {
								SteamAgeRevolution.proxy.spawnSmoke(this.getPos());
								SteamAgeRevolution.proxy.spawnSmoke(tubePositions[i2]);
							}
						}
					}
					return;
				}
				else if(block == ModulePneumatic.pneumaticTube && this.getWorld().getBlockState(currentPos)
						.getValue(BlockPneumaticTube.AXIS) == BlockLog.EnumAxis.fromFacingAxis(facing.getAxis())) {
					tubePositions[i] = currentPos;
					continue;
				}
				else
					return;
			}
		}
	}
}
