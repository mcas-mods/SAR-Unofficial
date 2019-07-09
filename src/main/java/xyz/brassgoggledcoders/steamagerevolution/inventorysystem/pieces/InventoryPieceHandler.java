package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;

public abstract class InventoryPieceHandler<H extends INBTSerializable<NBTTagCompound>> extends InventoryPiece {
	H handler;
	// Null in the case of basic inventory, only defined by recipe machines. TODO?
	@Nullable
	IOType type;

	public InventoryPieceHandler(String name, InventoryBasic parent, IOType type, H handler, int xPositions,
			int yPositions) {
		super(name, parent, xPositions, yPositions);
		this.handler = handler;
		this.type = type;
	}

	@Nullable
	public IOType getType() {
		return type;
	}

	public H getHandler() {
		return handler;
	}
}