package xyz.brassgoggledcoders.steamagerevolution.utils;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityHasCache extends TileEntitySlowTick implements IHasCache {
	private boolean hasCache;

	@Override
	public void updateTile() {
		if(world.isRemote)
			return;
		if(!hasCache) {
			recalculateCache(getWorld(), getPos(), this.getWorld().getBlockState(getPos()), null);
		}
	}

	@Override
	public void recalculateCache(World worldIn, BlockPos pos, IBlockState state, BlockPos fromPos) {
		this.hasCache = true;
	}
}
