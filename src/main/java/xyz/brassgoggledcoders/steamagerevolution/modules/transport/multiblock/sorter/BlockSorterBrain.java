package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockGUIBase;

public class BlockSorterBrain extends BlockGUIBase<TileEntitySorterBrain> {

	public BlockSorterBrain(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySorterBrain.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySorterBrain();
	}

}
