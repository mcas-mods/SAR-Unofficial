package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces;

import org.apache.commons.lang3.tuple.Pair;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.io.MultiFluidIO;

public class InventoryPieceMultiFluid extends InventoryPieceIO<MultiFluidIO> {
	public InventoryPieceMultiFluid(MultiFluidIO handler, int[] xPos, int[] yPos) {
		super(handler, xPos, yPos);
	}

	public InventoryPieceMultiFluid(MultiFluidIO handler, int xPos, int yPos) {
		this(handler, new int[] { xPos }, new int[] { yPos });
	}

	public InventoryPieceMultiFluid(MultiFluidIO handler, Pair<int[], int[]> posPair) {
		this(handler, posPair.getLeft(), posPair.getRight());
	}
}