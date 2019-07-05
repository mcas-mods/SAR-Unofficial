package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.util.INBTSerializable;

public class InventoryPieceHandler<H extends INBTSerializable> extends InventoryPiece {
	H handler;

	public InventoryPieceHandler(H handler, int[] xPositions, int[] yPositions) {
		super(xPositions, yPositions);
		this.handler = handler;
	}

	public InventoryPieceHandler(H handler, int xPosition, int yPosition) {
		this(handler, new int[] { xPosition }, new int[] { yPosition });
	}

	public InventoryPieceHandler(H handler, Pair<int[], int[]> posPair) {
		this(handler, posPair.getLeft(), posPair.getRight());
	}

	public H getHandler() {
		return handler;
	}
}