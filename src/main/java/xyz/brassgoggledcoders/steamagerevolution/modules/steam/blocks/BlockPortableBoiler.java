package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks;

import com.teamacronymcoders.base.blocks.BlockGUIBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.items.ItemBlockPortableBoiler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityPortableBoiler;

public class BlockPortableBoiler extends BlockGUIBase<TileEntityPortableBoiler> {

	public BlockPortableBoiler() {
		super(Material.IRON, "portable_boiler");
		setItemBlock(new ItemBlockPortableBoiler(this));
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityPortableBoiler.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityPortableBoiler();
	}

	@Override
	public void getDrops(NonNullList<ItemStack> result, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		getTileEntity(world, pos).ifPresent(te -> result.add(te.createDrop()));
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if(willHarvest) {
			return true;
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te,
			ItemStack stack) {
		super.harvestBlock(world, player, pos, state, te, stack);
		world.setBlockToAir(pos);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if(stack.hasTagCompound()) {
			worldIn.getTileEntity(pos).deserializeNBT(stack.getTagCompound().getCompoundTag("teData"));
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(playerIn.getHeldItem(hand).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			getTileEntity(worldIn, pos)
					.ifPresent(te -> FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing));
		}
		else {
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
		return false;
	}
}
