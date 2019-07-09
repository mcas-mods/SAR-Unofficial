package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;

//TODO Move the power to define GUI behaviour into InventoryPiece (callbacks from GUI methods)
public abstract class InventoryPiece {
	final String name;
	// TODO Make these a single integer by default, and have
	// InventoryPieceItemHandler override that
	final int xPos[];
	final int yPos[];
	// TODO Currently unused
	public final InventoryBasic parent;

	// Name MUST be unique within the inventory
	public InventoryPiece(String name, InventoryBasic parent, int[] xPos, int[] yPos) {
		this.name = name;
		this.parent = parent;
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

	public int getX(int i) {
		return xPos[i];
	}

	public int getY(int i) {
		return yPos[i];
	}

	public void drawScreenCallback(GuiInventory gui, int mouseX, int mouseY, float partialTicks) {
		// NO-OP
	}

	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		// NO-OP
	}

}