package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import java.awt.Color;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "mezz.jei.api.IRecipeWrapper", modid = "jei", striprefs = true)
public class EvaporatorRecipe implements IRecipeWrapper {
	public final FluidStack input;
	public final FluidStack output;
	public final int ticksToProcess;

	public EvaporatorRecipe(FluidStack input, FluidStack output, int ticksToProcess) {
		this.input = input;
		this.output = output;
		this.ticksToProcess = ticksToProcess;
	}

	private static ArrayList<EvaporatorRecipe> recipeList = new ArrayList<EvaporatorRecipe>();

	public static void addRecipe(FluidStack input, FluidStack output, int ticksToProcess) {
		recipeList.add(new EvaporatorRecipe(input, output, ticksToProcess));
	}

	public static EvaporatorRecipe getRecipe(FluidStack input) {
		for(EvaporatorRecipe r : recipeList) {
			if(r.input.isFluidEqual(input)) {
				return r;
			}
		}
		return null;
	}

	public static ArrayList<EvaporatorRecipe> getRecipeList() {
		return recipeList;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(FluidStack.class, input);
		ingredients.setOutput(FluidStack.class, output);
	}

	@Override
	@Optional.Method(modid = "jei")
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(input.amount + "mB", 100, 130, Color.gray.getRGB());
		minecraft.fontRenderer.drawString(output.amount + "mB", 170, 130, Color.gray.getRGB());
		minecraft.fontRenderer.drawString(ticksToProcess + " ticks to evaporate", 170, 140, Color.red.getRGB());
	}
}
