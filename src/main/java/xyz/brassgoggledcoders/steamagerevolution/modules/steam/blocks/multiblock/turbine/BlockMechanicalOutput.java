package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.turbine;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.turbine.TileEntityMechanicalOutput;

public class BlockMechanicalOutput extends BlockTEBase<TileEntityMechanicalOutput> {

	public BlockMechanicalOutput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityMechanicalOutput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityMechanicalOutput();
	}

}
