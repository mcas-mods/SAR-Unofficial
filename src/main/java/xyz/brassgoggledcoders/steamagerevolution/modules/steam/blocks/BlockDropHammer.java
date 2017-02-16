package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockTEBase;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityDropHammer;

public class BlockDropHammer extends BlockTEBase<TileEntityDropHammer> {

	public BlockDropHammer(Material mat, String name) {
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityDropHammer.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityDropHammer();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(ItemStackUtils.isItemNonNull(heldItem)) {
			getTileEntity(world, pos).dieType = "gear";
			return true;
		}
		return false;
	}

}
