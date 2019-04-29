package xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.ModuleMining;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockGrinderInput extends BlockMultiblockBase<TileEntityGrinderInput> {

	public BlockGrinderInput() {
		super(Material.IRON, "grinder_input");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityGrinderInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityGrinderInput();
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		// TODO Only do slight damage unless the grinder is running, in which case do
		// loads.
		entityIn.attackEntityFrom(ModuleMining.damageSourceGrinder, 3F);
	}
}
