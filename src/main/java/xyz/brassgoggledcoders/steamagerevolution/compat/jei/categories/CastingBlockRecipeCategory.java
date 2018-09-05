package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.tileentities.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class CastingBlockRecipeCategory extends SARRecipeCategory<SARMachineRecipe> {

    public static final String uid = "castingblock";

    public CastingBlockRecipeCategory() {
        super(uid, "Casting Block");
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SARMachineRecipe recipeWrapper, IIngredients ingredients) {
        recipeLayout.getFluidStacks().init(0, true, 1, 1, 20, 60, TileEntityCastingBench.inputCapacity, true, null);
        recipeLayout.getItemStacks().init(1, false, 58, 23);

        recipeLayout.getFluidStacks().set(0, ingredients.getInputs(FluidStack.class).get(0));
        recipeLayout.getItemStacks().set(1, ingredients.getOutputs(ItemStack.class).get(0));
    }

}
