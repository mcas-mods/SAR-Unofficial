package xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.BlockGUIBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityFluidIO;

public class BlockFluidIO extends BlockGUIBase<TileEntityFluidIO> {

	public BlockFluidIO() {
		super(Material.IRON, "fluid_io");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFluidIO.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFluidIO();
	}

}
