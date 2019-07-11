package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;

public class InventoryPieceProgressBar extends InventoryPiece {
	public InventoryPieceProgressBar(InventoryRecipe parent, int xPos, int yPos) {
		super("progress", parent, xPos, yPos);
	}

	@Override
	public void drawScreenCallback(GuiInventory gui, int mouseX, int mouseY, float partialTicks) {
		if(gui.isPointInRegion(this.getX(), this.getY(), 24, 16, mouseX, mouseY)) {

			if(this.getInventory().getCurrentMaxTicks() == 0) {
				gui.drawHoveringText(TextFormatting.RED.toString() + "No recipe", mouseX, mouseY); // TODO
				// Localization
			}
			else if(GuiScreen.isShiftKeyDown()) {
				gui.drawHoveringText(this.getInventory().getCurrentTicks() + "/"
						+ this.getInventory().getCurrentMaxTicks() + " ticks", mouseX, mouseY);
			}
			else {
				gui.drawHoveringText(this.getInventory().getCurrentTicks() / 20 + "/"
						+ this.getInventory().getCurrentMaxTicks() / 20 + " seconds", mouseX, mouseY);
			}
		}
	}

	@Override
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		gui.mc.renderEngine.bindTexture(gui.guiTexture);
		int progress = this.getInventory().getCurrentTicks();// TODO this needs packet synced
		int total = this.getInventory().getCurrentMaxTicks();
		int progressScaled = progress != 0 && total != 0 ? progress * 24 / total : 0;
		gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY(), 176, 83, progressScaled + 1, 16);
	}

	// FIXME This cast is known ok, but I should probably find a way to remove the
	// warning
	private InventoryRecipe getInventory() {
		return (InventoryRecipe) parent;
	}

}