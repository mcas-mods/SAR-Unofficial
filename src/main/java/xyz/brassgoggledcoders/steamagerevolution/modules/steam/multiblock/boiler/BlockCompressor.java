package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCompressor extends BlockTEBase<TileEntityCompressor> {

	public BlockCompressor(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		// TODO Auto-generated method stub
		return TileEntityCompressor.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		// TODO Auto-generated method stub
		return new TileEntityCompressor();
	}

}
