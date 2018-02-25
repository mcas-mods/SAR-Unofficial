package xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityBasicFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;

public class BlockBasicFluidTank extends BlockTEBase<TileEntityBasicFluidTank> {
	public BlockBasicFluidTank(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityBasicFluidTank.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityBasicFluidTank();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
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
		return true;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityBasicFluidTank te = getTileEntity(worldIn, pos).get();
		if(!worldIn.isRemote && te != null) {
			if(playerIn.isSneaking()) {
				playerIn.sendStatusMessage(TextUtils.representTankContents(te.tank), true);
				return true;
			}
			else {
				return FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing);
			}
		}
		return false;
	}
}
