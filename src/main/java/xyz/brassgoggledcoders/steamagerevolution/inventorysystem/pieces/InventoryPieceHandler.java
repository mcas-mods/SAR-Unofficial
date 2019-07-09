package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;

public class InventoryPieceHandler<H extends INBTSerializable<NBTTagCompound>> extends InventoryPiece {
	H handler;

	public InventoryPieceHandler(String name, InventoryBasic parent, H handler, int[] xPositions, int[] yPositions) {
		super(name, parent, xPositions, yPositions);
		this.handler = handler;
	}

	public InventoryPieceHandler(String name, InventoryBasic parent, H handler, int xPosition, int yPosition) {
		this(name, parent, handler, new int[] { xPosition }, new int[] { yPosition });
	}

	public InventoryPieceHandler(String name, InventoryBasic parent, H handler, Pair<int[], int[]> posPair) {
		this(name, parent, handler, posPair.getLeft(), posPair.getRight());
	}

	public H getHandler() {
		return handler;
	}
}