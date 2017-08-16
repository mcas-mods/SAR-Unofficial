package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.kiln;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockMultiblockBase;

public class BlockKilnDoor extends BlockMultiblockBase<TileEntityKilnDoor> {

	public static final PropertyBool OPEN = PropertyBool.create("open");

	public BlockKilnDoor(Material material, String name) {
		super(material, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(OPEN, Boolean.valueOf(false)));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if(state.getValue(OPEN).booleanValue()) {
			return Block.NULL_AABB;
		}
		else {
			return Block.FULL_BLOCK_AABB;
		}
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getValue(OPEN).booleanValue();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		worldIn.setBlockState(pos, state.cycleProperty(OPEN), 2);
		return true;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityKilnDoor.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityKilnDoor();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(OPEN, meta == 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(OPEN) ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {OPEN});
	}

}