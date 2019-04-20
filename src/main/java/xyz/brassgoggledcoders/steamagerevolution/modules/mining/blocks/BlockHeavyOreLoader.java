package xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks;

import com.teamacronymcoders.base.blocks.BlockSidedBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.TileEntityHeavyOreLoader;

public class BlockHeavyOreLoader extends BlockSidedBase<TileEntityHeavyOreLoader> {

	public BlockHeavyOreLoader() {
		super(Material.IRON, "heavyore_loader");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityHeavyOreLoader.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityHeavyOreLoader();
	}

}
