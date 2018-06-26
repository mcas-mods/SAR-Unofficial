package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.tileentities.TileEntitySteelworksSteelOutput;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockSteelworksSteelOutput extends BlockMultiblockBase<TileEntitySteelworksSteelOutput> {

	public BlockSteelworksSteelOutput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySteelworksSteelOutput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySteelworksSteelOutput();
	}

}