package xyz.brassgoggledcoders.steamagerevolution.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHasCache {
	public void recalculateCache(World worldIn, BlockPos pos, IBlockState state, BlockPos fromPos);
}
