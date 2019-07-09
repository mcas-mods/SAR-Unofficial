package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;

public class InventoryPieceProgressBar extends InventoryPiece {
	public InventoryPieceProgressBar(InventoryBasic parent, int xPos, int yPos) {
		super("progress", parent, new int[] { xPos }, new int[] { yPos });
	}

}