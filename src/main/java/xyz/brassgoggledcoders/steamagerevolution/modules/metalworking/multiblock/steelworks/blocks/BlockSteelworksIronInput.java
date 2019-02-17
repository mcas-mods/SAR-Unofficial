package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.tileentities.TileEntitySteelworksIronInput;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockSteelworksIronInput extends BlockMultiblockBase<TileEntitySteelworksIronInput> {

	public BlockSteelworksIronInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySteelworksIronInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySteelworksIronInput();
	}

}