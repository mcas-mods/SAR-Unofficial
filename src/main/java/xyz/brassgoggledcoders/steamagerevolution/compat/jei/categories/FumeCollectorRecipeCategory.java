package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities.FumeCollectorRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities.TileEntityFumeCollector;

public class FumeCollectorRecipeCategory extends SARRecipeCategory<FumeCollectorRecipe> {

    public static final String uid = "fumecollector";

    public FumeCollectorRecipeCategory() {
        super(uid, "Fume Collector");
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FumeCollectorRecipe recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 2, 23);
        recipeLayout.getFluidStacks().init(1, false, 55, 1, 20, 60, TileEntityFumeCollector.outputCapacity, true, null);

        recipeLayout.getItemStacks().set(0, ingredients.getInputs(ItemStack.class).get(0));
        recipeLayout.getFluidStacks().set(1, ingredients.getOutputs(FluidStack.class).get(0));
    }

}
