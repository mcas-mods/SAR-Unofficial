package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityDropHammer;

public class BlockDropHammer extends BlockTEBase<TileEntityDropHammer> {
	public BlockDropHammer(Material mat, String name) {
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityDropHammer.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityDropHammer();
	}

}
