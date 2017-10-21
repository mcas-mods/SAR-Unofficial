package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.function.Function;

import com.teamacronymcoders.base.multiblock.rectangular.PartPosition;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiblockPositional<C extends SARRectangularMultiblockControllerBase>
		extends BlockMultiblockBase<C> {

	public static final PropertyEnum<PartPosition> position = PartPosition.createProperty("position");

	public BlockMultiblockPositional(Class<? extends TileEntityMultiblockBase> tileClass,
			Function<World, TileEntityMultiblockBase<C>> tileEntityCreator, Material material, String name) {
		super(tileClass, tileEntityCreator, material, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(position, PartPosition.UNKNOWN));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, position);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(position, this.getTileEntity(worldIn, pos).getPartPosition());
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
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
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return worldIn.getBlockState(pos.offset(side)).getBlock() == this ? false : true;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

}
