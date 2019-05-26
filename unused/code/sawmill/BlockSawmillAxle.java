package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.sawmill;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockSawmillAxle extends BlockMultiblockBase<TileEntitySawmillAxle> {

	public BlockSawmillAxle(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySawmillAxle.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySawmillAxle();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntitySawmillAxle te = getTileEntity(worldIn, pos).get();
		if(te != null && te.isConnected()) {
			playerIn.sendMessage(new TextComponentString("Speed: " + te.getMultiblockController().speed));
			playerIn.sendMessage(TextUtils.representTankContents(te.getMultiblockController().steamTank));
			playerIn.sendMessage(TextUtils.representInventoryContents(te.getMultiblockController().inputInventory));
			playerIn.sendMessage(TextUtils.representInventoryContents(te.getMultiblockController().outputInventory));
			return true;
		}

		return false;
	}

}