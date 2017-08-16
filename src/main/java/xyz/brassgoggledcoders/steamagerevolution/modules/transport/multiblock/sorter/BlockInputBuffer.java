package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockGUIBase;

public class BlockInputBuffer extends BlockGUIBase<TileEntityInputBuffer> {

	public BlockInputBuffer(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityInputBuffer.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityInputBuffer();
	}

}
