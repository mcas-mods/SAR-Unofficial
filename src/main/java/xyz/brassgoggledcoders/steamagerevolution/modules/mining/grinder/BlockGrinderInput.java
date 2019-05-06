package xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.ModuleMining;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockGrinderInput extends BlockMultiblockBase<TileEntityGrinderInput> {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
	
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
		float damage = 3F;
		if(this.getTileEntity(worldIn, pos).isPresent() && this.getTileEntity(worldIn, pos).get().isConnected()) {
			if(this.getTileEntity(worldIn, pos).get().getMultiblockController().getCurrentProgress() > 0) {
				damage = 20F;
			}
		}
		entityIn.attackEntityFrom(ModuleMining.damageSourceGrinder, damage);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return AABB;
    }

}
