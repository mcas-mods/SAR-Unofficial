package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SteamHammerRecipe {

	public final ItemStack input;
	public final ItemStack output;
	@Nullable
	public final String dieType;

	private SteamHammerRecipe(ItemStack input, ItemStack output, @Nullable String dieType) {
		this.input = input;
		this.output = output;
		this.dieType = dieType;
	}

	private static ArrayList<SteamHammerRecipe> recipeList = new ArrayList<SteamHammerRecipe>();

	public static void addSteamHammerRecipe(ItemStack input, ItemStack output) {
		addSteamHammerRecipe(input, output, "");
	}

	public static void addSteamHammerRecipe(ItemStack input, ItemStack output, String dieType) {
		if(ItemStackUtils.isItemNonNull(input))
			recipeList.add(new SteamHammerRecipe(input, output, dieType));
	}

	@Nullable
	public static ItemStack getResult(ItemStack input, String dieType) {
		for(SteamHammerRecipe r : recipeList) {
			if(dieType == null || r.dieType.equals(dieType)) {
				if(OreDictionary.itemMatches(r.input, input, false)) {
					return r.output;
				}
			}
		}
		return null;
	}

	public static void registerRecipes() {
		addSteamHammerRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL));
		addSteamHammerRecipe(new ItemStack(Blocks.DIRT), new ItemStack(Items.DIAMOND), "test");
	}
}
