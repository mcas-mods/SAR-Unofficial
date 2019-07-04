package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces;

import org.apache.commons.lang3.tuple.Pair;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.io.ItemIO;

public class InventoryPieceItem extends InventoryPieceIO<ItemIO> {
	public InventoryPieceItem(ItemIO handler, int[] xPos, int[] yPos) {
		super(handler, xPos, yPos);
	}

	public InventoryPieceItem(ItemIO handler, int xPos, int yPos) {
		this(handler, new int[] { xPos }, new int[] { yPos });
	}

	public InventoryPieceItem(ItemIO handler, Pair<int[], int[]> posPair) {
		this(handler, posPair.getLeft(), posPair.getRight());
	}
}