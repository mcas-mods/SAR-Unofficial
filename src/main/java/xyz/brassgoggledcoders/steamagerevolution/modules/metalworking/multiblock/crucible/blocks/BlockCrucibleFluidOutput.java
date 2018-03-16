package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.tileentities.TileEntityCrucibleFluidOutput;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockGUIBase;

public class BlockCrucibleFluidOutput extends BlockMultiblockGUIBase<TileEntityCrucibleFluidOutput> {

	public BlockCrucibleFluidOutput(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityCrucibleFluidOutput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityCrucibleFluidOutput();
	}

}
