package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking;

import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHammer extends ItemBase {

	public ItemHammer(String name) {
		super(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(ToolMaterial.IRON.getMaxUses());
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return state.getMaterial() == Material.ROCK ? 5F : 0F;
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return blockIn.getMaterial() == Material.ROCK;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		if(player.world.isRemote || player.capabilities.isCreativeMode) {
			return false;
		}
		IBlockState state = player.world.getBlockState(pos);
		Block block = state.getBlock();
		ItemStack oreStack = new ItemStack(block.getItemDropped(state, player.getRNG(), 0), 1,
				block.damageDropped(state));
		// TODO Looping not entirely necessary since we know both source and target
		// unlike recipes
		for(String metal : ModuleMetalworking.knownMetalTypes) {
			if(OreDictionary.containsMatch(false, OreDictionary.getOres("ore" + metal, false), oreStack)) {
				ItemStack dust = OreDictUtils.getPreferredItemStack("dust" + metal);
				for(int i = 0; i < ModuleMetalworking.dustCount; i++) {
					EntityItem entityitem = new EntityItem(player.world, (double) pos.getX(), (double) pos.getY(),
							(double) pos.getZ(), dust);
					entityitem.setDefaultPickupDelay();
					player.world.spawnEntity(entityitem);
				}
				player.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
				itemstack.damageItem(1, player);
				return true;
			}
		}
		return false;
	}
}
