package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces;

public abstract class InventoryPiece {
	public final int xPos[];
	public final int yPos[];

	public InventoryPiece(int[] xPos, int[] yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public int getX(int i) {
		return xPos[i];
	}

	public int getY(int i) {
		return yPos[i];
	}
}