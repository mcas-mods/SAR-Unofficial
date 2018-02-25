package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer;

import java.awt.Color;
import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.oredict.OreDictionary;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class SteamHammerRecipe implements IRecipeWrapper {

	public final ItemStack input;
	public final ItemStack output;
	public final String dieType;

	public SteamHammerRecipe(ItemStack input, ItemStack output, String dieType) {
		this.input = input;
		this.output = output;
		this.dieType = dieType;
	}

	private static ArrayList<SteamHammerRecipe> recipeList = new ArrayList<SteamHammerRecipe>();

	public static void addSteamHammerRecipe(ItemStack input, ItemStack output) {
		addSteamHammerRecipe(input, output, "");
	}

	public static void addSteamHammerRecipe(ItemStack input, ItemStack output, @Nonnull String dieType) {
		if(!input.isEmpty())
			recipeList.add(new SteamHammerRecipe(input, output, dieType));
	}

	@Nullable
	public static ItemStack getResult(ItemStack input, String dieType) {
		for(SteamHammerRecipe r : recipeList) {
			if(dieType.isEmpty() || r.dieType.equalsIgnoreCase(dieType)) {
				if(OreDictionary.itemMatches(r.input, input, false)) {
					return r.output;
				}
			}
		}
		return null;
	}

	public static ArrayList<SteamHammerRecipe> getRecipeList() {
		return recipeList;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
	}

	@Override
	@Optional.Method(modid = "jei")
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if(!dieType.isEmpty()) {
			minecraft.fontRenderer.drawString("Requires die: " + dieType, recipeWidth / 2, recipeHeight / 2,
					Color.gray.getRGB());
		}
	}
}
