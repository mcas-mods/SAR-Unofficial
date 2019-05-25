package xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.tileentities.TileEntitySteelworksCarbonInput;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockSteelworksCarbonInput extends BlockMultiblockBase<TileEntitySteelworksCarbonInput> {

	public BlockSteelworksCarbonInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySteelworksCarbonInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySteelworksCarbonInput();
	}

}