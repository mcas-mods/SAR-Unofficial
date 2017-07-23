package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class MoltenMetalRecipe {
	public final ItemStack solid;
	public final Fluid melted;

	public MoltenMetalRecipe(ItemStack metal, Fluid melted) {
		this.solid = metal;
		this.melted = melted;
	}

	private static ArrayList<MoltenMetalRecipe> recipeList = new ArrayList<MoltenMetalRecipe>();

	public static void addMelting(ItemStack metal, Fluid fluid) {
		SteamAgeRevolution.instance.getLogger()
				.devInfo("Registering melting for " + metal.getDisplayName() + " " + fluid.getName());
		metal.setCount(1);
		recipeList.add(new MoltenMetalRecipe(metal, fluid));
	}

	public static ItemStack getSolidFromMolten(Fluid melted) {
		for(MoltenMetalRecipe r : recipeList) {
			if(r.melted == melted) {
				return r.solid;
			}
		}
		return ItemStack.EMPTY;
	}

	public static Fluid getMoltenFromSolid(ItemStack solid) {
		solid.setCount(1);
		for(MoltenMetalRecipe r : recipeList) {
			if(solid.isItemEqual(r.solid)) {
				return r.melted;
			}
		}
		return null;
	}
}
