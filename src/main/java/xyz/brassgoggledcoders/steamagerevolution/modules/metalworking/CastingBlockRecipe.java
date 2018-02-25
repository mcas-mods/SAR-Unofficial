package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking;

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
public class CastingBlockRecipe implements IRecipeWrapper {
	public final FluidStack input;
	public final ItemStack output;

	public CastingBlockRecipe(FluidStack fluid, ItemStack solid) {
		this.input = fluid;
		this.output = solid;
	}

	private static ArrayList<CastingBlockRecipe> recipeList = new ArrayList<CastingBlockRecipe>();

	public static void addRecipe(FluidStack fluid, ItemStack solid) {
		SteamAgeRevolution.instance.getLogger().devInfo(
				"Registering casting recipe for " + fluid.getLocalizedName() + "into" + solid.getDisplayName());
		recipeList.add(new CastingBlockRecipe(fluid, solid));
	}

	public static CastingBlockRecipe getRecipe(FluidStack molten) {
		for(CastingBlockRecipe r : recipeList) {
			if(r.input.isFluidEqual(molten) && r.input.amount == molten.amount) {
				return r;
			}
		}
		return null;
	}

	public static ArrayList<CastingBlockRecipe> getRecipeList() {
		return recipeList;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(FluidStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
	}

	@Override
	@Optional.Method(modid = "jei")
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(input.amount + "mB", 100, 130, Color.gray.getRGB());
	}
}
