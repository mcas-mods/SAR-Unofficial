package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.tileentities;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySlowTick;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.ModulePneumatic;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockSender;

public class TileEntitySender extends TileEntitySlowTick {

	public int maxDistance = 15;

	@Override
	public void updateTile() {
		EnumFacing facing = this.getWorld().getBlockState(getPos()).getValue(BlockSender.FACING);
		for(int i = 0; i < maxDistance; i++) {
			Block block = this.getWorld().getBlockState(getPos().offset(facing, i)).getBlock();
			if(block != ModulePneumatic.pneumaticTube) {
				if(block == ModulePneumatic.router) {
					FMLLog.warning("Tube");
					return;
				}
				else
					return;
			}
		}
	}
}
