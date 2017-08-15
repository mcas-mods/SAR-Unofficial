package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.kiln;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockMultiblockBase;

public class BlockKilnSteamInput extends BlockMultiblockBase<TileEntityKilnSteamInput> {

	public BlockKilnSteamInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityKilnSteamInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityKilnSteamInput();
	}

}