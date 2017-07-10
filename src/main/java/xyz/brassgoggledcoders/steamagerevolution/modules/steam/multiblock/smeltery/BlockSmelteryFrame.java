package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSmelteryFrame extends BlockTEBase<TileEntitySmelteryFrame> {

	public BlockSmelteryFrame(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySmelteryFrame.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySmelteryFrame();
	}

}