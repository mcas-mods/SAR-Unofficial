package xyz.brassgoggledcoders.steamagerevolution.modules.management.tileentities;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySlowTick;
import xyz.brassgoggledcoders.steamagerevolution.modules.management.ModuleManagement;
import xyz.brassgoggledcoders.steamagerevolution.modules.management.blocks.BlockSender;

public class TileEntitySender extends TileEntitySlowTick {

	public int maxDistance = 15;

	@Override
	public void updateTile() {
		EnumFacing facing = this.getWorld().getBlockState(getPos()).getValue(BlockSender.FACING);
		for(int i = 0; i < maxDistance; i++) {
			Block block = this.getWorld().getBlockState(getPos().offset(facing, i)).getBlock();
			if(block != ModuleManagement.pneumatic_tube) {
				if(block == ModuleManagement.router) {
					FMLLog.warning("Tube");
					return;
				}
				else
					return;
			}
		}
	}
}
