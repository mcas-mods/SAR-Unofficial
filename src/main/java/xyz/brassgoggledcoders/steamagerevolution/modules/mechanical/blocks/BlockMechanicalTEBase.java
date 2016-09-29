package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntitySpinMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.SpinUtils;

public abstract class BlockMechanicalTEBase<T extends TileEntity> extends BlockTEBase<T> {

	public BlockMechanicalTEBase(Material material, String name) {
		super(material, name);
	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		for(ISpinHandler handler : SpinUtils.getHandlersNearby(world, pos)) {
			handler.setSpeed(0);
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		((TileEntitySpinMachine) world.getTileEntity(pos)).onNeighbourChange(neighbor);
	}
}
