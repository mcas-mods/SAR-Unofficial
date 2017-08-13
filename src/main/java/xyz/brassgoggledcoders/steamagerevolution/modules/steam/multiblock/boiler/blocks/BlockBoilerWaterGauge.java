package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerPart;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerWaterGauge;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;

public class BlockBoilerWaterGauge extends BlockMultiblockBase<TileEntityBoilerWaterGauge> {

	public BlockBoilerWaterGauge(Material material, String name) {
		super(material, name);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityBoilerPart te = getTileEntity(worldIn, pos);
		if(te != null && te.isConnected()) {
			playerIn.sendStatusMessage(TextUtils.representTankContents(te.getMultiblockController().waterTank), true);
			return true;
		}
		return false;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityBoilerWaterGauge.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityBoilerWaterGauge();
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

}