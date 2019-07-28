package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import java.awt.Color;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.ControllerBoiler;

public class InventoryPieceTemperatureGauge extends InventoryPiece<InventoryCraftingMachine> {

	public InventoryPieceTemperatureGauge(int xPos, int yPos) {
		super(xPos, yPos, 88, 166, 4, 44, -1);
	}

	@Override
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		if(this.enclosingInv.enclosingMachine instanceof ControllerBoiler) {
			ControllerBoiler boiler = (ControllerBoiler) this.enclosingInv.enclosingMachine;
			// if(boiler.temperature > 0) {
			gui.drawGradientRect(gui.guiLeft + this.getX() - offset, gui.guiTop + this.getY() - offset,
					gui.guiLeft + this.getX() - offset + this.width, gui.guiTop + this.getY() - offset + this.height,
					Color.RED.getRGB(), Color.BLUE.getRGB());
			// }
		}
		super.backgroundLayerCallback(gui, partialTicks, mouseX, mouseY);
	}

}
