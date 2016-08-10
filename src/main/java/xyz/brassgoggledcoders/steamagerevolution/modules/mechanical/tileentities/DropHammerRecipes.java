package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class DropHammerRecipes {
	private static final DropHammerRecipes INSTANCE = new DropHammerRecipes();
	private Map<ItemStack, ItemStack> recipeList = Maps.<ItemStack, ItemStack> newHashMap();

	public static DropHammerRecipes instance() {
		return INSTANCE;
	}

	// TODO IMC
	public void addDropHammerRecipe(ItemStack input, ItemStack output) {
		SteamAgeRevolution.instance.getLogger().devInfo("Hammer recipe: " + input + " > " + output + " added");
		if(getResult(input) != null) {
			SteamAgeRevolution.instance.getLogger().info(("Ignored hammer recipe with conflicting input: " + input));
			return;
		}
		this.recipeList.put(input, output);
	}

	public void addDropHammerRecipe(Block input, ItemStack output) {
		this.addDropHammerRecipe(new ItemStack(input), output);
	}

	// TODO Make this less Mojang.
	@Nullable
	public ItemStack getResult(ItemStack input) {
		for(Entry<ItemStack, ItemStack> entry : this.recipeList.entrySet()) {
			if(this.compareItemStacks(input, entry.getKey())) {
				return entry.getValue();
			}
		}

		return null;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem()
				&& (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getRecipeList() {
		return recipeList;
	}
}
