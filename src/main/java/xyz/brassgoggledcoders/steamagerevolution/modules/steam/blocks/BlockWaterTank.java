package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler.TileEntityWaterTank;

public class BlockWaterTank extends BlockTEBase<TileEntityWaterTank> {

	public BlockWaterTank(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityWaterTank.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityWaterTank();
	}

}
