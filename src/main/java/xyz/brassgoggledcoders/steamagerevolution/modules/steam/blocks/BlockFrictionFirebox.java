package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityFrictionFirebox;

public class BlockFrictionFirebox extends BlockTEBase {
	public BlockFrictionFirebox(Material mat, String name) {
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFrictionFirebox.class;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFrictionFirebox();
	}

}
