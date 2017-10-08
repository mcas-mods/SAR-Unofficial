package xyz.brassgoggledcoders.steamagerevolution.compat.minetweaker;

import java.util.ArrayList;

import blusunrize.immersiveengineering.common.util.compat.crafttweaker.CraftTweakerHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.SteamFurnaceRecipe;

@ZenClass("mods.steamagerevolution.SteamFurnace")
public class SteamFurnaceTweaker {

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output, String dieType) {
		SteamFurnaceRecipe r =
				new SteamFurnaceRecipe(CraftTweakerHelper.toStack(input), CraftTweakerHelper.toStack(output));
		CraftTweakerAPI.apply(new Add(r));
	}

	private static class Add implements IAction {
		private final SteamFurnaceRecipe recipe;

		public Add(SteamFurnaceRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			SteamFurnaceRecipe.getRecipeList().add(recipe);
		}

		@Override
		public String describe() {
			return "Adding Steam Furnace Recipe for " + recipe.output.getDisplayName();
		}
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toStack(output)));
	}

	private static class Remove implements IAction {
		private final ItemStack output;

		public Remove(ItemStack output) {
			this.output = output;
		}

		@Override
		public void apply() {
			// TODO
			ArrayList<SteamFurnaceRecipe> toRemove = new ArrayList<SteamFurnaceRecipe>();
			for(SteamFurnaceRecipe r : SteamFurnaceRecipe.getRecipeList()) {
				if(r.output == output) {
					toRemove.add(r);
				}
			}
			SteamFurnaceRecipe.getRecipeList().removeAll(toRemove);
		}

		@Override
		public String describe() {
			return "Removing Steam Furnace Recipe for " + output.getDisplayName();
		}
	}
}
