package xyz.brassgoggledcoders.steamagerevolution.utils;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockHasCache<T extends TileEntityHasCache> extends BlockTEBase<T> {

	public BlockHasCache(Material material, String name) {
		super(material, name);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		((IHasCache) worldIn.getTileEntity(pos)).recalculateCache(worldIn, pos, state, null);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		((IHasCache) worldIn.getTileEntity(pos)).recalculateCache(worldIn, pos, state, fromPos);
	}
}
