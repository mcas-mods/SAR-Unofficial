package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.blocks;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.tileentities.TileEntityFurnaceSteamInput;

public class BlockFurnaceSteamInput extends BlockTEBase<TileEntityFurnaceSteamInput> {

	public BlockFurnaceSteamInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFurnaceSteamInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFurnaceSteamInput();
	}

}