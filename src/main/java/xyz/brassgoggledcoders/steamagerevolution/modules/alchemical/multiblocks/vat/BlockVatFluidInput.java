package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockVatFluidInput extends BlockMultiblockBase<TileEntityVatFluidInput> {

	public BlockVatFluidInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityVatFluidInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityVatFluidInput();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote)
			return false;
		TileEntityVatFluidInput te = getTileEntity(worldIn, pos);
		if(te != null && te.isConnected()) {
			if(playerIn.getHeldItem(hand).getItem() == Items.DIAMOND) {
				te.getMultiblockController().fluidInput.fluids.clear();
			}
			else if(playerIn.getHeldItem(hand).getItem() == Items.STICK) {
				te.getMultiblockController().itemInput.setStackInSlot(0, ItemStack.EMPTY);
				te.getMultiblockController().itemInput.setStackInSlot(1, ItemStack.EMPTY);
				te.getMultiblockController().itemInput.setStackInSlot(2, ItemStack.EMPTY);
			}
			for(int i = 0; i < te.getMultiblockController().fluidInput.getFluidTypes(); i++) {
				playerIn.sendStatusMessage(
						TextUtils.representFluidStack(te.getMultiblockController().fluidInput.fluids.get(i)), false);
			}
			playerIn.sendStatusMessage(TextUtils.representInventoryContents(te.getMultiblockController().itemInput),
					false);
			playerIn.sendStatusMessage(TextUtils.representTankContents(te.getMultiblockController().output), false);
			return true;
		}
		return false;
	}

}