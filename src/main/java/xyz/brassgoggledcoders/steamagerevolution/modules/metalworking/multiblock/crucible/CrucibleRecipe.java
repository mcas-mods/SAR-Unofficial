package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible;

import java.awt.Color;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class CrucibleRecipe implements IRecipeWrapper {
	public final ItemStack input;
	public final FluidStack output;

	public CrucibleRecipe(ItemStack metal, FluidStack fluid) {
		input = metal;
		output = fluid;
	}

	private static ArrayList<CrucibleRecipe> recipeList = new ArrayList<CrucibleRecipe>();

	public static void addRecipe(ItemStack metal, FluidStack fluid) {
		SteamAgeRevolution.instance.getLogger()
				.devInfo("Registering melting for " + metal.getDisplayName() + " into " + fluid.getLocalizedName());
		recipeList.add(new CrucibleRecipe(metal, fluid));
	}

	public static CrucibleRecipe getRecipe(ItemStack solid) {
		for(CrucibleRecipe r : recipeList) {
			if(solid.isItemEqual(r.input)) {
				return r;
			}
		}
		return null;
	}

	public static ArrayList<CrucibleRecipe> getRecipeList() {
		return recipeList;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(FluidStack.class, output);
	}

	@Override
	@Optional.Method(modid = "jei")
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(output.amount + "mB", 170, 130, Color.gray.getRGB());
	}
}
