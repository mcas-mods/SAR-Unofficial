package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.items.ItemDie;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.tileentities.TileEntitySteamHammerHammer;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockSteamHammerHammer extends BlockMultiblockBase<TileEntitySteamHammerHammer> {

	public BlockSteamHammerHammer(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySteamHammerHammer.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySteamHammerHammer();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = playerIn.getHeldItem(hand);
		if(!held.isEmpty() && held.getItem() == ModuleMetalworking.die) {
			TileEntitySteamHammerHammer hammer = (TileEntitySteamHammerHammer) worldIn.getTileEntity(pos);
			if(hammer != null && hammer.isConnected()) {
				hammer.getMultiblockController().dieType = ItemDie.getDieNameFromMeta(held);
				held.setCount(0);
				return true;
			}
		}
		return false;
	}

}
