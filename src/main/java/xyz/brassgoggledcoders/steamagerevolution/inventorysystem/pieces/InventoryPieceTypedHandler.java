package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;

public class InventoryPieceTypedHandler<H extends INBTSerializable<NBTTagCompound>> extends InventoryPieceHandler<H> {
	IOType type;

	public InventoryPieceTypedHandler(String name, InventoryBasic parent, IOType type, H handler, int[] xPositions,
			int[] yPositions) {
		super(name, parent, handler, xPositions, yPositions);
		this.type = type;
	}

	public InventoryPieceTypedHandler(String name, InventoryBasic parent, IOType type, H handler, int xPosition,
			int yPosition) {
		this(name, parent, type, handler, new int[] { xPosition }, new int[] { yPosition });
	}

	public InventoryPieceTypedHandler(String name, InventoryBasic parent, IOType type, H handler,
			Pair<int[], int[]> posPair) {
		this(name, parent, type, handler, posPair.getLeft(), posPair.getRight());
	}

	public IOType getType() {
		return type;
	}
}