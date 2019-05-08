package xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockGrinderFrame extends BlockMultiblockBase<TileEntityGrinderFrame> {

	public BlockGrinderFrame() {
		super(Material.IRON, "grinder_frame");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityGrinderFrame.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityGrinderFrame();
	}
}
