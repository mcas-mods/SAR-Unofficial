package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

//TODO Move the power to define GUI behaviour into InventoryPiece (callbacks from GUI methods)
public abstract class InventoryPiece {
	final String name;
	final int xPos[];
	final int yPos[];

	// Name MUST be unique within the inventory
	public InventoryPiece(String name, int[] xPos, int[] yPos) {
		this.name = name;
		// These define the piece's positions in GuiInventory. Optional if you have no
		// GUI I suppose (but will probably crash if you try to do that)
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