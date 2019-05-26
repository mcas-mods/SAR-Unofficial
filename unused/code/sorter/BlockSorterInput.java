package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.BlockGUIBase;

public class BlockSorterInput extends BlockGUIBase<TileEntitySorterInput> {

	public BlockSorterInput(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySorterInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySorterInput();
	}

}
