package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;

public class BlockModuleEditor extends BlockTEBase {

	public BlockModuleEditor() {
		super(Material.IRON, "module_editor");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityModuleEditor.class;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityModuleEditor();
	}

}
