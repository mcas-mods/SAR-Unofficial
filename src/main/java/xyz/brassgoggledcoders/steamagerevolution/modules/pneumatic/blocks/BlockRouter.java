package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.tileentities.TileEntityRouter;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockGUIBase;

public class BlockRouter extends BlockGUIBase<TileEntityRouter> {

	public BlockRouter(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityRouter.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityRouter();
	}

}
