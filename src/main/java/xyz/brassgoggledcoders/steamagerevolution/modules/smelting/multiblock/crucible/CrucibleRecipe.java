package xyz.brassgoggledcoders.steamagerevolution.modules.smelting.multiblock.crucible;

import java.util.ArrayList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Optional.Interface(iface = "mezz.jei.api.IRecipeWrapper", modid = "jei", striprefs = true)
public class CrucibleRecipe implements IRecipeWrapper {
	public final ItemStack solid;
	public final Fluid melted;

	public CrucibleRecipe(ItemStack metal, Fluid melted) {
		this.solid = metal;
		this.melted = melted;
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
			if(r.melted == melted) {
				return r.solid;
			}
		}
		return ItemStack.EMPTY;
	}

	public static Fluid getMoltenFromSolid(ItemStack solid) {
		solid.setCount(1);
		for(CrucibleRecipe r : recipeList) {
			if(solid.isItemEqual(r.solid)) {
				return r.melted;
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
		ingredients.setInput(ItemStack.class, solid);
		ingredients.setOutput(FluidStack.class, new FluidStack(melted, 1)); // FIXME
	}
}
