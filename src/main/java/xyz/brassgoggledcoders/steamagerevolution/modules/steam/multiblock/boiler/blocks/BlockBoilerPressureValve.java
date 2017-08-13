package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerPressureValve;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockMultiblockBase;

public class BlockBoilerPressureValve extends BlockMultiblockBase<TileEntityBoilerPressureValve> {

	public BlockBoilerPressureValve(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		// TODO Auto-generated method stub
		return TileEntityBoilerPressureValve.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		// TODO Auto-generated method stub
		return new TileEntityBoilerPressureValve();
	}
}
