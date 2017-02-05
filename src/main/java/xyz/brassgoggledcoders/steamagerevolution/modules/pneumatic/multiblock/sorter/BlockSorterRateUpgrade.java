package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.multiblock.sorter;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSorterRateUpgrade extends BlockTEBase<TileEntitySorterRateUpgrade> {

	public BlockSorterRateUpgrade(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySorterRateUpgrade.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySorterRateUpgrade();
	}

}