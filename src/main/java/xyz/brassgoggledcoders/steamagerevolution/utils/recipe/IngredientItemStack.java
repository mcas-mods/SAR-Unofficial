package xyz.brassgoggledcoders.steamagerevolution.utils.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class IngredientItemStack extends Ingredient {
	// Modify protected to public...
	public IngredientItemStack(ItemStack... stacks) {
		super(stacks);
	}
}
