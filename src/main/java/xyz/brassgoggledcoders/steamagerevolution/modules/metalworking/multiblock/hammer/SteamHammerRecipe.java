package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer;

import java.awt.Color;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

// FIXME
@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class SteamHammerRecipe extends SARMachineRecipe {

	public final String dieType;

	public SteamHammerRecipe(String crafter, ItemStack[] itemInputs, FluidStack[] fluidInputs, int ticksToProcess,
			int steamUsePerCraft, ItemStack[] itemOutputs, FluidStack[] fluidOutputs, String dieType) {
		super(crafter, null, null, ticksToProcess, steamUsePerCraft, itemOutputs, fluidOutputs);
		this.dieType = dieType;
	}

	public SteamHammerRecipe(String crafter, ItemStack[] itemInputs, FluidStack[] fluidInputs, int ticksToProcess,
			int steamUsePerCraft, ItemStack[] itemOutputs, FluidStack[] fluidOutputs) {
		this(crafter, itemInputs, fluidInputs, ticksToProcess, steamUsePerCraft, itemOutputs, fluidOutputs, "");
	}

	@Override
	@Optional.Method(modid = "jei")
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if(!dieType.isEmpty()) {
			minecraft.fontRenderer.drawString("Requires die: " + dieType, recipeWidth / 2, recipeHeight / 2,
					Color.gray.getRGB());
		}
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
	}
}
