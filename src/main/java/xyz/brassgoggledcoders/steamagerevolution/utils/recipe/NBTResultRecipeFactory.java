package xyz.brassgoggledcoders.steamagerevolution.utils.recipe;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class NBTResultRecipeFactory implements IRecipeFactory {

	@Override
	public IRecipe parse(final JsonContext context, final JsonObject json) {
		final String group = JsonUtils.getString(json, "group", "");
		final NonNullList<Ingredient> ingredients = RecipeUtil.parseShapeless(context, json);
		final ItemStack result = ShapedRecipes.deserializeItem(JsonUtils.getJsonObject(json, "result"), true);

		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(JsonUtils.getJsonObject(json, "result").get("tag").getAsString(),
				JsonUtils.getJsonObject(json, "result").get("value").getAsString());
		result.setTagCompound(tag);

		return new ShapelessRecipes(group, result, ingredients);
	}

}
