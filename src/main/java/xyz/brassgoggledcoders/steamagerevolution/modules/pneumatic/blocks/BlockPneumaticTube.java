package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks;

import com.teamacronymcoders.base.api.BaseAPI;
import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPneumaticTube extends BlockBase {

	public static final PropertyEnum<BlockLog.EnumAxis> AXIS =
			PropertyEnum.<BlockLog.EnumAxis> create("axis", BlockLog.EnumAxis.class);

	public BlockPneumaticTube(Material mat, String name) {
		super(mat, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumAxis.X));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(ItemStackUtils.isItemNonNull(heldItem) && heldItem.hasCapability(BaseAPI.TOOL_CAPABILITY, side)) {
			state.cycleProperty(AXIS);
			return true;
		}

		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getStateFromMeta(meta).withProperty(AXIS,
				BlockLog.EnumAxis.fromFacingAxis(facing.getOpposite().getAxis()));
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
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		switch(meta) {
			case 0:
				return this.getDefaultState().withProperty(AXIS, EnumAxis.X);
			case 1:
				return this.getDefaultState().withProperty(AXIS, EnumAxis.Z);
			case 2:
				return this.getDefaultState().withProperty(AXIS, EnumAxis.Y);
		}
		return null;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		BlockLog.EnumAxis axis = state.getValue(AXIS);
		if(axis == EnumAxis.Z)
			return 1;
		else if(axis == EnumAxis.Y)
			return 2;
		else
			return 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {AXIS});
	}

}
