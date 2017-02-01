package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityDropHammerAnvil;

public class BlockDropHammerAnvil extends BlockTEBase<TileEntityDropHammerAnvil> {
	public BlockDropHammerAnvil(Material mat, String name) {
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityDropHammerAnvil.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityDropHammerAnvil();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(worldIn.getTileEntity(pos) instanceof TileEntityDropHammerAnvil) { // It certainly should be.
			TileEntityDropHammerAnvil anvil = (TileEntityDropHammerAnvil) worldIn.getTileEntity(pos);
			if(anvil.handler.getStackInSlot(1) != null && heldItem == null) {
				playerIn.inventory.addItemStackToInventory(anvil.handler.extractItem(1, 1, false));
			}
			if(heldItem != null && anvil.handler.insertItem(0, heldItem, true) == null) {
				heldItem = anvil.handler.insertItem(0, heldItem, false);
			}
			SteamAgeRevolution.instance.getLogger().devInfo("" + anvil.handler.getStackInSlot(0));
			SteamAgeRevolution.instance.getLogger().devInfo("" + anvil.handler.getStackInSlot(1));
			return true;
		}

		return false;
	}
}
