package xyz.brassgoggledcoders.steamagerevolution.modules.smelting.multiblock.crucible;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Optional.Interface(iface = "mezz.jei.api.IRecipeWrapper", modid = "jei", striprefs = true)
public class CrucibleRecipe implements IRecipeWrapper {
	public final ItemStack input;
	public final Fluid output;

	public CrucibleRecipe(ItemStack metal, Fluid melted) {
		this.input = metal;
		this.output = melted;
	}

	private static ArrayList<CrucibleRecipe> recipeList = new ArrayList<CrucibleRecipe>();

	public static void addMelting(ItemStack metal, Fluid fluid) {
		SteamAgeRevolution.instance.getLogger()
				.devInfo("Registering melting for " + metal.getDisplayName() + " " + fluid.getName());
		metal.setCount(1);
		recipeList.add(new CrucibleRecipe(metal, fluid));
	}

	public static ItemStack getSolidFromMolten(Fluid melted) {
		for(CrucibleRecipe r : recipeList) {
			if(r.output == melted) {
				return r.input;
			}
		}
		return ItemStack.EMPTY;
	}

	public static Fluid getMoltenFromSolid(ItemStack solid) {
		solid.setCount(1);
		for(CrucibleRecipe r : recipeList) {
			if(solid.isItemEqual(r.input)) {
				return r.output;
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
		ingredients.setOutput(FluidStack.class, new FluidStack(output, 1)); // FIXME
	}

	@Override
	@Optional.Method(modid = "jei")
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		// minecraft.fontRenderer.drawString(ouput.amount + "mB", 170, 130, Color.gray.getRGB()); TODO
	}
}
