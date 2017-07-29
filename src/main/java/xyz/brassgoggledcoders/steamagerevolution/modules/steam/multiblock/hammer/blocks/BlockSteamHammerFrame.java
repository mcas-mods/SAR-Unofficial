package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.tileentities.TileEntitySteamHammerFrame;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockMultiblockBase;

public class BlockSteamHammerFrame extends BlockMultiblockBase<TileEntitySteamHammerFrame> {

	public BlockSteamHammerFrame(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySteamHammerFrame.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySteamHammerFrame();
	}
}
