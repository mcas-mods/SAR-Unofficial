package xyz.brassgoggledcoders.steamagerevolution.compat.minetweaker;

import java.util.ArrayList;

import blusunrize.immersiveengineering.common.util.compat.crafttweaker.CraftTweakerHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.SteamHammerRecipe;

@ZenClass("mods.steamagerevolution.SteamHammer")
public class SteamHammerTweaker {

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output) {
		addRecipe(input, output, "");
	}

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output, String dieType) {
		SteamHammerRecipe r =
				new SteamHammerRecipe(CraftTweakerHelper.toStack(input), CraftTweakerHelper.toStack(output), dieType);
		CraftTweakerAPI.apply(new Add(r));
	}

	private static class Add implements IAction {
		private final SteamHammerRecipe recipe;

		public Add(SteamHammerRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			SteamHammerRecipe.getRecipeList().add(recipe);
		}

		@Override
		public String describe() {
			return "Adding Steam Hammer Recipe for " + recipe.output.getDisplayName();
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
			ArrayList<SteamHammerRecipe> toRemove = new ArrayList<SteamHammerRecipe>();
			for(SteamHammerRecipe r : SteamHammerRecipe.getRecipeList()) {
				if(r.output == output) {
					toRemove.add(r);
				}
			}
			SteamHammerRecipe.getRecipeList().removeAll(toRemove);
		}

		@Override
		public String describe() {
			return "Removing Steam Hammer Recipe for " + output.getDisplayName();
		}
	}
}
