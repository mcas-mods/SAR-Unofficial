package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.tileentities.TileEntitySteamHammerHammer;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockMultiblockBase;

public class BlockSteamHammerHammer extends BlockMultiblockBase<TileEntitySteamHammerHammer> {

	public BlockSteamHammerHammer(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySteamHammerHammer.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySteamHammerHammer();
	}

}
