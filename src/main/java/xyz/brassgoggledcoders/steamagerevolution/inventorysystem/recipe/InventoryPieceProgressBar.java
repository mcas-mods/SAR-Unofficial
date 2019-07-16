package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPiece;

public class InventoryPieceProgressBar extends InventoryPiece<InventoryRecipe> {

	public InventoryPieceProgressBar(InventoryRecipe parent, int xPos, int yPos) {
		super("progress", parent, xPos, yPos, 50, 166, 22, 15, 0);
		// TODO Formal subpiece system
		new InventoryPieceRecipeError(parent, xPos + 10, yPos - 10);
	}

	@Override
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		gui.mc.renderEngine.bindTexture(GuiInventory.guiTexture);
		int progress = parent.getCurrentTicks();
		int total = parent.getMaxTicks();
		int progressScaled = progress != 0 && total != 0 ? progress * 24 / total : 0;
		gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY(), 176, 83, progressScaled + 1, 16);
	}

	@Override
	public String getTooltip() {
		if(parent.getMaxTicks() == 0) {
			return TextFormatting.RED.toString() + "No recipe"; // TODO
			// Localization
		}
		else if(GuiScreen.isShiftKeyDown()) {
			return parent.getCurrentTicks() + "/" + parent.getMaxTicks() + " ticks";
		}
		else {
			return parent.getCurrentTicks() / 20 + "/" + parent.getMaxTicks() / 20 + " seconds";
		}
	}
}