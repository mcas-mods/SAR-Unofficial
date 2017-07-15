package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.tileentities.TileEntityAlloyFurnaceController;

public class BlockAlloyFurnaceController extends BlockTEBase<TileEntityAlloyFurnaceController> {

	public BlockAlloyFurnaceController(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityAlloyFurnaceController.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityAlloyFurnaceController();
	}

}