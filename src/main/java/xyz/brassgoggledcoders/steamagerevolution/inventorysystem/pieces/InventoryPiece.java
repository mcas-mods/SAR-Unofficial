package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;

//TODO Move the power to define GUI behaviour into InventoryPiece (callbacks from GUI methods)
public abstract class InventoryPiece {
	final String name;
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

	public int getX(int i) {
		return xPos[i];
	}

	public int getY(int i) {
		return yPos[i];
	}

	public String getName() {
		return name;
	}
}