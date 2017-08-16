package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockMultiblockBase;

public class BlockSteelworksSteamInput extends BlockMultiblockBase<TileEntitySteelworksSteamInput> {

	public BlockSteelworksSteamInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySteelworksSteamInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySteelworksSteamInput();
	}

}