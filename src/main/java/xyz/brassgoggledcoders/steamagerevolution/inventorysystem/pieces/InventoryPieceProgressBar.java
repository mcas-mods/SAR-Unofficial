package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IRecipeMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;

public class InventoryPieceProgressBar extends InventoryPiece {
	public InventoryPieceProgressBar(InventoryRecipe parent, int xPos, int yPos) {
		super("progress", parent, xPos, yPos);
	}

	@Override
	public void drawScreenCallback(GuiInventory gui, int mouseX, int mouseY, float partialTicks) {
		if(gui.isPointInRegion(this.getX(), this.getY(), 24, 16, mouseX, mouseY)) {

			if(this.get().getCurrentMaxTicks() == 0) {
				gui.drawHoveringText(TextFormatting.RED.toString() + "No recipe", mouseX, mouseY); // TODO
				// Localization
			}
			else if(GuiScreen.isShiftKeyDown()) {
				gui.drawHoveringText(this.get().getCurrentProgress() + "/" + this.get().getCurrentMaxTicks() + " ticks",
						mouseX, mouseY);
			}
			else {
				gui.drawHoveringText(
						this.get().getCurrentProgress() / 20 + "/" + this.get().getCurrentMaxTicks() / 20 + " seconds",
						mouseX, mouseY);
			}
		}
	}

	@Override
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		gui.mc.renderEngine.bindTexture(gui.guiTexture);
		int progress = this.get().getCurrentProgress();// TODO this needs packet synced
		int total = this.get().getCurrentMaxTicks();
		int progressScaled = progress != 0 && total != 0 ? progress * 24 / total : 0;
		gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY(), 176, 83, progressScaled + 1, 16);
	}

	// FIXME Dodgy casts
	@Deprecated
	private IRecipeMachine<InventoryRecipe> get() {
		return((IRecipeMachine<InventoryRecipe>) parent.parent);
	}

}