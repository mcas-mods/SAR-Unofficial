package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;

//TODO Move the power to define GUI behaviour into InventoryPiece (callbacks from GUI methods)
public abstract class InventoryPiece {
	String name;
	final int xPos;
	final int yPos;

	// Name MUST be unique within the inventory
	public InventoryPiece(String name, int xPos, int yPos) {
		this.name = name;
		// These define the piece's positions in GuiInventory. Optional if you have no
		// GUI I suppose (but will probably crash if you try to do that, I've not tested
		// since it's outside my usecase)
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public String getName() {
		return name;
	}

	// GUI Related methods below this line

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public void drawScreenCallback(GuiInventory gui, int mouseX, int mouseY, float partialTicks) {
		// NO-OP
	}

	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		// NO-OP
	}

}