package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockGUIBase;

public class BlockDistillerRadiator extends BlockGUIBase<TileEntityDistillerRadiator> {

	public BlockDistillerRadiator(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityDistillerRadiator.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityDistillerRadiator();
	}
}