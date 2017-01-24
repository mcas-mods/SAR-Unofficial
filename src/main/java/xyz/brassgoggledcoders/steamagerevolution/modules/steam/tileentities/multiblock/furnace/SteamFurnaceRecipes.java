package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.furnace;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class SteamFurnaceRecipes {
	private static final SteamFurnaceRecipes INSTANCE = new SteamFurnaceRecipes();
	private Map<ItemStack, ItemStack> recipeList = Maps.<ItemStack, ItemStack> newHashMap();

	public static SteamFurnaceRecipes instance() {
		return INSTANCE;
	}

	// TODO IMC
	public void addRecipe(ItemStack input, ItemStack output) {
		this.recipeList.put(input, output);
	}

	public void addRecipe(Block input, ItemStack output) {
		this.addRecipe(new ItemStack(input), output);
	}

	@Nullable
	public ItemStack getResult(ItemStack input) {
		for(Entry<ItemStack, ItemStack> entry : this.recipeList.entrySet()) {
			if(ItemStack.areItemsEqual(input, entry.getKey())) {
				return entry.getValue();
			}
		}

		return FurnaceRecipes.instance().getSmeltingResult(input);
	}

	public Map<ItemStack, ItemStack> getRecipeList() {
		return recipeList;
	}

	public static void registerRecipes() {

	}
}
