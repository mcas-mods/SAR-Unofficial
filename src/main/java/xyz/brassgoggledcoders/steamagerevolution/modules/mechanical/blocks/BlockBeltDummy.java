package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class BlockBeltDummy extends BlockBase {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool SPINNING = PropertyBool.create("spinning");

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.1D, 0.1D, 0.1D, 0.9D, 0.9D, 0.9D);

	public BlockBeltDummy(String type) {
		super(Material.CLOTH, type + "_belt_dummy");
		this.setBlockUnbreakable();
		this.setDefaultState(
				this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN).withProperty(SPINNING, false));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(state.getValue(SPINNING)) {
			entityIn.attackEntityFrom(SteamAgeRevolution.belt, 3.0F + worldIn.rand.nextInt(3));
			if(worldIn.rand.nextBoolean()) {
				entityIn.addVelocity(worldIn.rand.nextDouble() / 2, worldIn.rand.nextDouble() / 2,
						worldIn.rand.nextDouble() / 2);
			}
			else {
				entityIn.addVelocity(-(worldIn.rand.nextDouble() / 2), -(worldIn.rand.nextDouble() / 2),
						-(worldIn.rand.nextDouble() / 2));
			}
			entityIn.playSound(SoundEvents.ENTITY_BLAZE_HURT, 3.0F, 1.0F);
		}
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta <= EnumFacing.VALUES.length) {
			return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta)).withProperty(SPINNING, false);
		}
		else {
			return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta / 2)).withProperty(SPINNING,
					true);
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		if(state.getValue(SPINNING))
			return state.getValue(FACING).getIndex() * 2;
		else
			return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING, SPINNING});
	}
}
