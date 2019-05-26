package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSorterFrame extends BlockTEBase<TileEntitySorterFrame> {

	public BlockSorterFrame(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySorterFrame.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySorterFrame();
	}

}