package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMachine;

public abstract class SARRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	protected static IGuiHelper helper;
	Class<? extends IMachine> machine;

	IDrawableAnimated arrow;

	public SARRecipeCategory(Class<? extends IMachine> machine) {
		this.machine = machine;
		IDrawableStatic arrowDrawable = helper.createDrawable(
				new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/inventory.png"), 176, 83, 24, 17);
		this.arrow = helper.createAnimatedDrawable(arrowDrawable, 20, IDrawableAnimated.StartDirection.LEFT, false);
	}

	public static void setGuiHelper(IGuiHelper helper) {
		SARRecipeCategory.helper = helper;
	}

	@Override
	public String getUid() {
		return SteamAgeRevolution.MODID + ":" + IMachine.referenceMachinesList.get(machine).getUID();
	}

	@Override
	public String getTitle() {
		return IMachine.referenceMachinesList.get(machine).getLocalizedName();
	}

	@Override
	public String getModName() {
		return SteamAgeRevolution.MODNAME;
	}

	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/inventory.png"), 7, 4,
				162, 79);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		if(IMachine.referenceMachinesList.get(machine) instanceof IHasInventory) {
			IHasInventory<?> inventory = (IHasInventory<?>) IMachine.referenceMachinesList.get(machine);
			inventory.getInventory().inventoryPieces.forEach((name, piece) -> helper
					.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/inventory.png"),
							piece.textureX, piece.textureY, piece.width, piece.height)
					.draw(minecraft, piece.getX(), piece.getY()));
		}
	}
}
