package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;

public class InventoryPieceProgressBar extends InventoryPiece {

	public final InventoryRecipe parent;

	public InventoryPieceProgressBar(InventoryRecipe parent, int xPos, int yPos) {
		super("progress", xPos, yPos);
		this.parent = parent;
	}

	@Override
	public void drawScreenCallback(GuiInventory gui, int mouseX, int mouseY, float partialTicks) {
		if(gui.isPointInRegion(this.getX(), this.getY(), 24, 16, mouseX, mouseY)) {
			if(parent.getMaxTicks() == 0) {
				gui.drawHoveringText(TextFormatting.RED.toString() + "No recipe", mouseX, mouseY); // TODO
				// Localization
			}
			else if(GuiScreen.isShiftKeyDown()) {
				gui.drawHoveringText(parent.getCurrentTicks() + "/" + parent.getMaxTicks() + " ticks", mouseX, mouseY);
			}
			else {
				gui.drawHoveringText(parent.getCurrentTicks() / 20 + "/" + parent.getMaxTicks() / 20 + " seconds",
						mouseX, mouseY);
			}
		}
	}

	@Override
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		gui.mc.renderEngine.bindTexture(gui.guiTexture);
		int progress = parent.getCurrentTicks();// TODO this needs packet synced
		int total = parent.getMaxTicks();
		int progressScaled = progress != 0 && total != 0 ? progress * 24 / total : 0;
		gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY(), 176, 83, progressScaled + 1, 16);
	}

}