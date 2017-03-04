package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.multiblock.sorter;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockGUIBase;

public class BlockOutputBuffer extends BlockGUIBase<TileEntityOutputBuffer> {

	public BlockOutputBuffer(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityOutputBuffer.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityOutputBuffer();
	}

}
