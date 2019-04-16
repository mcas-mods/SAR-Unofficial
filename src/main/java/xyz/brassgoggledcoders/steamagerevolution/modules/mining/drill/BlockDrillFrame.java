package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockDrillFrame extends BlockMultiblockBase<TileEntityDrillFrame> {

	public BlockDrillFrame() {
		super(Material.IRON, "drill_frame");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityDrillFrame.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityDrillFrame();
	}

}
