package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.tileentities.TileEntityAlloyFurnaceFrame;

public class BlockAlloyFurnaceFrame extends BlockTEBase<TileEntityAlloyFurnaceFrame> {

	public BlockAlloyFurnaceFrame(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityAlloyFurnaceFrame.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityAlloyFurnaceFrame();
	}

}