package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;

public class BlockInfiniteSpinSource extends BlockTEBase {
	public BlockInfiniteSpinSource(Material mat, String name) {
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityInfiniteSpinSource.class;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityInfiniteSpinSource();
	}

}
