package xyz.brassgoggledcoders.steamagerevolution.utils;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class BaseRecipeHandler {
	private Map<ItemStack, ItemStack> recipeList = Maps.<ItemStack, ItemStack> newHashMap();

	@Nullable
	public ItemStack getResult(ItemStack input) {
		for(Entry<ItemStack, ItemStack> entry : this.recipeList.entrySet()) {
			if(OreDictionary.itemMatches(entry.getKey(), input, false)) {
				return entry.getValue();
			}
		}
		return null;
	}
}
