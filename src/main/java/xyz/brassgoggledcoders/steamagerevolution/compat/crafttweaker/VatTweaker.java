package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.steamagerevolution.Vat")
public class VatTweaker {
	// @ZenMethod
	// public static void addRecipe(ILiquidStack[] fluids, IItemStack[] stacks,
	// ILiquidStack output) {
	// // VatRecipe r = new
	// VatRecipeBuilder().setFluidOutputs(CTHelper.toFluidStack(output))
	// //
	// .setFluidInputs(Arrays.stream(fluids).map(CTHelper::toFluidStack).toArray(size
	// -> new FluidStack[size]))
	// //
	// .setItemInputs(Arrays.stream(stacks).map(CTHelper::toItemStack).toArray(size
	// -> new ItemStack[size]))
	// // .build();
	// // CraftTweakerAPI.apply(new Add(r));
	// }
	//
	// private static class Add implements IAction {
	// private final VatRecipe recipe;
	//
	// public Add(VatRecipe recipe) {
	// this.recipe = recipe;
	// }
	//
	// @Override
	// public void apply() {
	// VatRecipe.getRecipeList().add(recipe);
	// }
	//
	// @Override
	// public String describe() {
	// return "Adding Vat Recipe for " + recipe.output.getLocalizedName();
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
	// ArrayList<VatRecipe> toRemove = new ArrayList<VatRecipe>();
	// for(VatRecipe r : VatRecipe.getRecipeList()) {
	// if(r.output == output) {
	// toRemove.add(r);
	// }
	// }
	// VatRecipe.getRecipeList().removeAll(toRemove);
	// }
	//
	// @Override
	// public String describe() {
	// return "Removing Vat Recipe for " + output.getLocalizedName();
	// }
	// }
}
