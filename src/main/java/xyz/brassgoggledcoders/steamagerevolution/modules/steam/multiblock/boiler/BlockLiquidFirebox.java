package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLiquidFirebox extends BlockTEBase<TileEntityLiquidFirebox> {

	public BlockLiquidFirebox(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityLiquidFirebox.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityLiquidFirebox();
	}

}
