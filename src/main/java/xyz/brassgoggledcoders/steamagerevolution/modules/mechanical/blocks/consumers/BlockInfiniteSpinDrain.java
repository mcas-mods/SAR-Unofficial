package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers.TileEntityInfiniteSpinDrain;

public class BlockInfiniteSpinDrain extends BlockTEBase<TileEntityInfiniteSpinDrain> {
	public BlockInfiniteSpinDrain(Material mat, String name) {
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityInfiniteSpinDrain.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityInfiniteSpinDrain();
	}

}
