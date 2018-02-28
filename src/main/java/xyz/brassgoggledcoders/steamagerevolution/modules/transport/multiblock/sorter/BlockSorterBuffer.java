package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockGUIBase;

public class BlockSorterBuffer extends BlockGUIBase<TileEntitySorterBuffer> {

	public BlockSorterBuffer(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySorterBuffer.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySorterBuffer();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntitySorterBuffer te = getTileEntity(worldIn, pos).get();
		if(te != null && te.isConnected()) {
			playerIn.sendStatusMessage(new TextComponentString(EnumDyeColor.byDyeDamage(te.color).getDyeColorName()),
					true);
			ItemStack held = playerIn.getHeldItem(hand);
			if(!held.isEmpty() && held.getItem() instanceof ItemDye) {
				getTileEntity(worldIn, pos).get().color = EnumDyeColor.byDyeDamage(held.getMetadata()).getColorValue();
				return true;
			}
		}

		return false;
	}

}
