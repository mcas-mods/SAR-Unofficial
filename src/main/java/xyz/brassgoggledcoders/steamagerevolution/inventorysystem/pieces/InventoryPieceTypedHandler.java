package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;

public class InventoryPieceTypedHandler<H extends INBTSerializable> extends InventoryPieceHandler<H> {
	IOType type;

	public InventoryPieceTypedHandler(String name, IOType type, H handler, int[] xPositions, int[] yPositions) {
		super(name, handler, xPositions, yPositions);
		this.type = type;
	}

	public InventoryPieceTypedHandler(String name, IOType type, H handler, int xPosition, int yPosition) {
		this(name, type, handler, new int[] { xPosition }, new int[] { yPosition });
	}

	public InventoryPieceTypedHandler(String name, IOType type, H handler, Pair<int[], int[]> posPair) {
		this(name, type, handler, posPair.getLeft(), posPair.getRight());
	}

	public IOType getType() {
		return type;
	}
}