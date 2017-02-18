package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerPressureMonitor;

public class BlockBoilerPressureMonitor extends BlockTEBase<TileEntityBoilerPressureMonitor> {

	public BlockBoilerPressureMonitor(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		// TODO Auto-generated method stub
		return TileEntityBoilerPressureMonitor.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		// TODO Auto-generated method stub
		return new TileEntityBoilerPressureMonitor();
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TileEntityBoilerPressureMonitor te = getTileEntity(world, pos);
		if(te.isConnected() && te.getMultiblockController().isAssembled()) {
			return (int) Math.ceil((te.getMultiblockController().pressure));
		}
		return 0;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

}
