package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.blocks;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities.TileEntityIncenseBurner;

public class BlockIncenseBurner extends BlockTEBase<TileEntityIncenseBurner> {

	public BlockIncenseBurner() {
		super(Material.IRON, "incense_burner");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityIncenseBurner.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityIncenseBurner();
	}

}
