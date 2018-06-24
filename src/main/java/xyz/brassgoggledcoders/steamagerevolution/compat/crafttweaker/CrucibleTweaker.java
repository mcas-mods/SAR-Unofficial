package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.steamagerevolution.Crucible")
public class CrucibleTweaker {

	// @ZenMethod
	// public static void addRecipe(IItemStack input, ILiquidStack output) {
	// CrucibleRecipe r = new CrucibleRecipe(CTHelper.toItemStack(input),
	// CTHelper.toFluidStack(output));
	// CraftTweakerAPI.apply(new Add(r));
	// }
	//
	// private static class Add implements IAction {
	// private final CrucibleRecipe recipe;
	//
	// public Add(CrucibleRecipe recipe) {
	// this.recipe = recipe;
	// }
	//
	// @Override
	// public void apply() {
	// CrucibleRecipe.getRecipeList().add(recipe);
	// }
	//
	// @Override
	// public String describe() {
	// return "Adding Crucible Recipe for " + recipe.output.getLocalizedName();
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
	// ArrayList<CrucibleRecipe> toRemove = new ArrayList<CrucibleRecipe>();
	// for(CrucibleRecipe r : CrucibleRecipe.getRecipeList()) {
	// if(output.isFluidEqual(new FluidStack(r.output, 1))) {
	// toRemove.add(r);
	// }
	// }
	// CrucibleRecipe.getRecipeList().removeAll(toRemove);
	// }
	//
	// @Override
	// public String describe() {
	// return "Removing Crucible Recipe for " + output.getLocalizedName();
	// }
	// }
}
