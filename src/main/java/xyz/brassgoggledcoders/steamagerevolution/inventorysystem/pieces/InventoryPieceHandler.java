package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.util.INBTSerializable;

public class InventoryPieceHandler<H extends INBTSerializable> extends InventoryPiece {
	H handler;

	public InventoryPieceHandler(String name, H handler, int[] xPositions, int[] yPositions) {
		super(name, xPositions, yPositions);
		this.handler = handler;
	}

	public InventoryPieceHandler(String name, H handler, int xPosition, int yPosition) {
		this(name, handler, new int[] { xPosition }, new int[] { yPosition });
	}

	public InventoryPieceHandler(String name, H handler, Pair<int[], int[]> posPair) {
		this(name, handler, posPair.getLeft(), posPair.getRight());
	}

	public H getHandler() {
		return handler;
	}
}