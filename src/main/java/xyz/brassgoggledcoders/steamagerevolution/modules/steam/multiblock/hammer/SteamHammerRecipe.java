package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SteamHammerRecipe {

	public final ItemStack input;
	public final ItemStack output;
	public final String dieType;

	private SteamHammerRecipe(ItemStack input, ItemStack output, String dieType) {
		this.input = input;
		this.output = output;
		this.dieType = dieType;
	}

	private static ArrayList<SteamHammerRecipe> recipeList = new ArrayList<SteamHammerRecipe>();

	public static void addSteamHammerRecipe(ItemStack input, ItemStack output) {
		addSteamHammerRecipe(input, output, "");
	}

	public static void addSteamHammerRecipe(ItemStack input, ItemStack output, @Nonnull String dieType) {
		if(!input.isEmpty())
			recipeList.add(new SteamHammerRecipe(input, output, dieType));
	}

	@Nullable
	public static ItemStack getResult(ItemStack input, String dieType) {
		for(SteamHammerRecipe r : recipeList) {
			if(dieType.isEmpty() || r.dieType.equalsIgnoreCase(dieType)) {
				if(OreDictionary.itemMatches(r.input, input, false)) {
					return r.output;
				}
			}
		}
		return null;
	}
}
