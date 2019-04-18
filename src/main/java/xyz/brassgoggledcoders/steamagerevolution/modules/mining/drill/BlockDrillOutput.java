package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockDrillOutput extends BlockMultiblockBase<TileEntityDrillFrame> {
	
	public BlockDrillOutput() {
		super(Material.IRON, "drill_output");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityDrillOutput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityDrillOutput();
	}

}
