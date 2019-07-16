package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;

public class InventoryPieceItemHandler extends InventoryPieceHandler<ItemStackHandler> {

	int[] slotXs;
	int[] slotYs;

	public InventoryPieceItemHandler(String name, InventoryBasic parent, ItemStackHandlerSync handler, int[] slotXs,
			int[] slotYs) {
		this(name, parent, null, handler, slotXs, slotYs);
	}

	public InventoryPieceItemHandler(String name, InventoryBasic parent, IOType type, ItemStackHandlerSync handler,
			int slotXs[], int[] slotYs) {
		super(name, parent, type, handler, slotXs[0], slotYs[0], 72, 166, 18, 18);
		this.slotXs = slotXs;
		this.slotYs = slotYs;
		parent.itemPieces.put(name, this);
		// TODO
		if(parent instanceof InventoryRecipe) {
			if(type.equals(IOType.INPUT)) {
				((InventoryRecipe) parent).itemInputPieces.add(this);
			}
			else if(type.equals(IOType.OUTPUT)) {
				((InventoryRecipe) parent).itemOutputPieces.add(this);
			}
		}
	}

	public int getSlotPositionX(int slot) {
		return slotXs[slot];
	}

	public int getSlotPositionY(int slot) {
		return slotYs[slot];
	}
}
