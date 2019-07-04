package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces;

import org.apache.commons.lang3.tuple.Pair;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.io.FluidIO;

public class InventoryPieceFluid extends InventoryPieceIO<FluidIO> {
	public InventoryPieceFluid(FluidIO handler, int[] xPos, int[] yPos) {
		super(handler, xPos, yPos);
	}

	public InventoryPieceFluid(FluidIO handler, int xPos, int yPos) {
		this(handler, new int[] { xPos }, new int[] { yPos });
	}

	public InventoryPieceFluid(FluidIO handler, Pair<int[], int[]> posPair) {
		this(handler, posPair.getLeft(), posPair.getRight());
	}
}