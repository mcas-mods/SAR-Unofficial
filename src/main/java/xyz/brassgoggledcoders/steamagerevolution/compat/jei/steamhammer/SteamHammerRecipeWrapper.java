package xyz.brassgoggledcoders.steamagerevolution.compat.jei.steamhammer;

import java.awt.Color;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class SteamHammerRecipeWrapper implements IRecipeWrapper {

	public final ItemStack input;
	public final ItemStack output;
	public final String dieType;

	SteamHammerRecipeWrapper(ItemStack input, ItemStack output, String dieType) {
		this.input = input;
		this.output = output;
		this.dieType = dieType;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if(!dieType.isEmpty()) {
			minecraft.fontRenderer.drawString("Requires die: " + dieType, recipeWidth / 2, recipeHeight / 2,
					Color.gray.getRGB());
		}
	}

}
