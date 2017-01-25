package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.turbine;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTurbineFrame extends BlockTEBase<TileEntityTurbineFrame> {

	public BlockTurbineFrame(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityTurbineFrame.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityTurbineFrame();
	}

}
