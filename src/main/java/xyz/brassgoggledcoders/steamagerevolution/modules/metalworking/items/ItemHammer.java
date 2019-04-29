package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.items;

import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;

public class ItemHammer extends ItemBase {

	public ItemHammer() {
		super("hammer");
		setMaxStackSize(1);
		setMaxDamage(ToolMaterial.IRON.getMaxUses());
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return state.getMaterial() == Material.ROCK ? 5F : 0F;
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return blockIn.getMaterial() == Material.ROCK;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		if (player.world.isRemote || player.capabilities.isCreativeMode) {
			return false;
		}
		IBlockState state = player.world.getBlockState(pos);
		Block block = state.getBlock();
		ItemStack oreStack = new ItemStack(Item.getItemFromBlock(block));
		ItemStack dust = OreDictUtils.getPreferredItemStack(
				"dust" + OreDictionary.getOreName(OreDictionary.getOreIDs(oreStack)[0]).substring(3));
		if (!dust.isEmpty()) {
			// TODO Gotta be a better way to do this
			for (int i = 0; i < ModuleMetalworking.dustCount; i++) {
				EntityItem entityitem = new EntityItem(player.world, pos.getX(), pos.getY(), pos.getZ(), dust);
				entityitem.setDefaultPickupDelay();
				player.world.spawnEntity(entityitem);
			}
			player.world.setBlockToAir(pos);
			itemstack.damageItem(1, player);
			return true;
		}
		return false;
	}
}
