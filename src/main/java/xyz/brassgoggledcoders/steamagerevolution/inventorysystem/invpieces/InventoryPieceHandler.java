package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;

public class InventoryPieceHandler<H extends INBTSerializable> extends InventoryPiece {
	IOType type;
	H handler;

	public InventoryPieceHandler(IOType type, H handler, int[] xPositions, int[] yPositions) {
		super(xPositions, yPositions);
		this.handler = handler;
		this.type = type;
	}

	public InventoryPieceHandler(IOType type, H handler, int xPosition, int yPosition) {
		this(type, handler, new int[] { xPosition }, new int[] { yPosition });
	}

	public InventoryPieceHandler(IOType type, H handler, Pair<int[], int[]> posPair) {
		this(type, handler, posPair.getLeft(), posPair.getRight());
	}

	public H getHandler() {
		return handler;
	}
}