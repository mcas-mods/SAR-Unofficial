package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import java.awt.Color;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class DistillerRecipe implements IRecipeWrapper {
	public final FluidStack input;
	public final FluidStack output;
	public final ItemStack itemOutput;
	public final int ticksToProcess;

	public DistillerRecipe(FluidStack input, FluidStack output, ItemStack itemOutput, int ticksToProcess) {
		this.input = input;
		this.output = output;
		this.itemOutput = itemOutput;
		this.ticksToProcess = ticksToProcess;
	}

	private static ArrayList<DistillerRecipe> recipeList = new ArrayList<DistillerRecipe>();

	public static void addRecipe(FluidStack input, FluidStack output, ItemStack itemOutput, int ticksToProcess) {
		recipeList.add(new DistillerRecipe(input, output, itemOutput, ticksToProcess));
	}

	public static DistillerRecipe getRecipe(FluidStack input) {
		for(DistillerRecipe r : recipeList) {
			if(r.input.isFluidEqual(input)) {
				return r;
			}
		}
		return null;
	}

	public static ArrayList<DistillerRecipe> getRecipeList() {
		return recipeList;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(FluidStack.class, input);
		ingredients.setOutput(FluidStack.class, output);
		ingredients.setOutput(ItemStack.class, itemOutput);
	}

	@Override
	@Optional.Method(modid = "jei")
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(ticksToProcess + " ticks to distil", recipeWidth - 52, recipeHeight - 5,
				Color.red.getRGB());
	}
}
