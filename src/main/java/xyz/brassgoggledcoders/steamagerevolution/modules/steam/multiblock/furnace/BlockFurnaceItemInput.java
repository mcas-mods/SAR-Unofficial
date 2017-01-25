package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace;

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
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.items.ItemStackHandler;

public class BlockFurnaceItemInput extends BlockTEBase<TileEntityFurnaceItemInput> {

	public BlockFurnaceItemInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFurnaceItemInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFurnaceItemInput();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityFurnacePart tile = getTileEntity(world, pos);
		ItemStackHandler h = ((SteamFurnaceController) tile.getMultiblockController()).inputInventory;
		if(h.getStackInSlot(0) != null)
			FMLLog.warning("0 " + h.getStackInSlot(0).toString());
		if(h.getStackInSlot(1) != null)
			FMLLog.warning("1 " + h.getStackInSlot(1).toString());
		if(h.getStackInSlot(2) != null)
			FMLLog.warning("2 " + h.getStackInSlot(2).toString());
		return false;
	}

}