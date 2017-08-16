package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockGUIBase;

public class BlockExpansionEditor extends BlockGUIBase<TileEntityExpansionEditor> {
	public BlockExpansionEditor() {
		super(Material.IRON, "expansion_editor");
	}

	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState blockState) {
		return new TileEntityExpansionEditor();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityExpansionEditor.class;
	}
}
