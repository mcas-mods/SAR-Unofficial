package xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockGUIBase;

public class BlockTankValve extends BlockMultiblockGUIBase<TileEntityTankValve> {

	public BlockTankValve(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityTankValve.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityTankValve();
	}

}
