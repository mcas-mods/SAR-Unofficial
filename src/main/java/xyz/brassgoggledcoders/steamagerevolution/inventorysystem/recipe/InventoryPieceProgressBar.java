package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPiece;

public class InventoryPieceProgressBar extends InventoryPiece<InventoryCraftingMachine> {

	public InventoryPieceProgressBar(int xPos, int yPos) {
		super(xPos, yPos, 50, 166, 22, 15, 0);
		// TODO Formal subpiece system
		// FIXME Broken by the change to builders
		new InventoryPieceRecipeError(xPos + 10, yPos - 10);
	}

	@Override
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		gui.mc.renderEngine.bindTexture(GuiInventory.guiTexture);
		int progress = enclosingInv.getCurrentTicks();
		int total = enclosingInv.getMaxTicks();
		int progressScaled = progress != 0 && total != 0 ? progress * 24 / total : 0;
		gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY(), 50, 181, progressScaled, 16);
	}

	@Override
	public String getTooltip() {
		if(enclosingInv.getMaxTicks() == 0) {
			return TextFormatting.RED.toString()
					+ new TextComponentTranslation("sar.recipeerror.norecipe").getFormattedText();
		}
		else if(GuiScreen.isShiftKeyDown()) {
			return enclosingInv.getCurrentTicks() + "/" + enclosingInv.getMaxTicks() + " "
					+ new TextComponentTranslation("info.ticks").getFormattedText();
		}
		else {
			return enclosingInv.getCurrentTicks() / 20 + "/" + enclosingInv.getMaxTicks() / 20 + " "
					+ new TextComponentTranslation("info.seconds").getFormattedText();
		}
	}

	@Override
	public void mouseClickedCallback(int mouseButton) {
		if(Loader.isModLoaded("jei") && SARJEIPlugin.recipesGui != null) {
			// SARJEIPlugin.recipesGui
			// .showCategories(NonNullList.from(parent.parent.getUID().toLowerCase().replace("
			// ", ""),
			// parent.parent.getUID().toLowerCase().replace(" ", ""),
			// CrucibleRecipeCategory.uid));
		}
	}
}