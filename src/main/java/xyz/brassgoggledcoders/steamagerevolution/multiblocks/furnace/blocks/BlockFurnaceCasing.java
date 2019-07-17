package xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.machines.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace.tileentities.TileEntityFurnaceCasing;

public class BlockFurnaceCasing extends BlockMultiblockBase<TileEntityFurnaceCasing> {

	public BlockFurnaceCasing(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFurnaceCasing.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFurnaceCasing();
	}

}