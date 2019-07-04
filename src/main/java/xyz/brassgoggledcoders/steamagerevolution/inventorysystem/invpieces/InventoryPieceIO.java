package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.io.IO;

public abstract class InventoryPieceIO<TYPE extends IO> extends InventoryPiece {
	TYPE io;

	public InventoryPieceIO(TYPE io, int xPosition, int yPosition) {
		this(io, new int[] { xPosition }, new int[] { yPosition });
	}

	public InventoryPieceIO(TYPE io, int[] xPositions, int[] yPositions) {
		super(xPositions, yPositions);
		this.io = io;
	}

	public TYPE getIO() {
		return io;
	}
}