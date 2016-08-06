package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntityFrictionHeater;

public class BlockFrictionHeater extends BlockTEBase {
	public BlockFrictionHeater(Material mat, String name) {
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFrictionHeater.class;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFrictionHeater();
	}

}
