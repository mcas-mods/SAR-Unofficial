package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;

//TODO Move the power to define GUI behaviour into InventoryPiece (callbacks from GUI methods)
public abstract class InventoryPiece {
	String name;
	final int elementX;
	final int elementY;
	public final int textureX, width;
	public final int textureY, height;
	public final int backgroundOffset;

	// Name MUST be unique within the inventory
	// TODO Create a 'GUIPiece' subclass that holds texture and position info?
	public InventoryPiece(String name, int xPos, int yPos, int textureX, int textureY, int xSize, int ySize,
			int offset) {
		this.name = name;
		this.elementX = xPos;
		this.elementY = yPos;
		this.textureX = textureX;
		this.textureY = textureY;
		this.width = xSize;
		this.height = ySize;
		this.backgroundOffset = offset;
	}

	public String getName() {
		return name;
	}

	// GUI Related methods below this line

	public int getX() {
		return elementX;
	}

	public int getY() {
		return elementY;
	}

	public void drawScreenCallback(GuiInventory gui, int mouseX, int mouseY, float partialTicks) {
		// NO-OP
	}

	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		// NO-OP
	}

}