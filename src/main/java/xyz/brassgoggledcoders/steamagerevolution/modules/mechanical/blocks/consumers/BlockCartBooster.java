package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers.TileEntityCartBooster;

public class BlockCartBooster extends BlockTEBase<TileEntityCartBooster> {

	public BlockCartBooster(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityCartBooster.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityCartBooster();
	}

}