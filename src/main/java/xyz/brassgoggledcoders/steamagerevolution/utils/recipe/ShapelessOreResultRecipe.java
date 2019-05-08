package xyz.brassgoggledcoders.steamagerevolution.utils.recipe;

import com.google.gson.JsonObject;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ShapelessOreResultRecipe extends ShapelessOreRecipe {

	public ShapelessOreResultRecipe(ResourceLocation group, NonNullList<Ingredient> input, ItemStack result) {
		super(group, input, result);
	}

	public static class Factory implements IRecipeFactory {

		@Override
		public IRecipe parse(final JsonContext context, final JsonObject json) {
			final String group = JsonUtils.getString(json, "group", "");
			final NonNullList<Ingredient> ingredients = RecipeUtil.parseShapeless(context, json);
			final ItemStack result = OreDictUtils
					.getPreferredItemStack(JsonUtils.getString(JsonUtils.getJsonObject(json, "result"), "ore"));

			return new ShapelessOreResultRecipe(group.isEmpty() ? null : new ResourceLocation(group), ingredients,
					result);
		}
	}

}
