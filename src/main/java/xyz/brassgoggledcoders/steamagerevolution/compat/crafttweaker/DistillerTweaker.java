package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.steamagerevolution.Distiller")
public class DistillerTweaker {
	//
	// @ZenMethod
	// public static void addRecipe(ILiquidStack input, ILiquidStack output, IItemStack itemOutput, int ticks) {
	// DistillerRecipe r = new DistillerRecipe(CTHelper.toFluidStack(input), CTHelper.toFluidStack(output),
	// CTHelper.toItemStack(itemOutput), ticks);
	// CraftTweakerAPI.apply(new Add(r));
	// }
	//
	// private static class Add implements IAction {
	// private final DistillerRecipe recipe;
	//
	// public Add(DistillerRecipe recipe) {
	// this.recipe = recipe;
	// }
	//
	// @Override
	// public void apply() {
	// // RecipeRegistry.getRecipeList().add(recipe);
	// }
	//
	// @Override
	// public String describe() {
	// return "Adding Distiller Recipe for " + recipe.output.getLocalizedName();
	// }
	// }
	//
	// @ZenMethod
	// public static void removeRecipe(ILiquidStack output) {
	// CraftTweakerAPI.apply(new Remove(CTHelper.toFluidStack(output)));
	// }
	//
	// private static class Remove implements IAction {
	// private final FluidStack output;
	//
	// public Remove(FluidStack output) {
	// this.output = output;
	// }
	//
	// @Override
	// public void apply() {
	// // TODO
	// ArrayList<DistillerRecipe> toRemove = new ArrayList<DistillerRecipe>();
	// // for(DistillerRecipe r : DistillerRecipe.getRecipeList()) {
	// // if(output.isFluidEqual(new FluidStack(r.output, 1))) {
	// // toRemove.add(r);
	// // }
	// // }
	// // DistillerRecipe.getRecipeList().removeAll(toRemove);
	// }
	//
	// @Override
	// public String describe() {
	// return "Removing Distiller Recipe for " + output.getLocalizedName();
	// }
	// }
}
