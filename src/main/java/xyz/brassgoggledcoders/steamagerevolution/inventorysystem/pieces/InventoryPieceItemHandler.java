package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.ItemStackHandlerSync;

public class InventoryPieceItemHandler extends InventoryPieceHandler<ItemStackHandlerSync> {

	int[] slotXs;
	int[] slotYs;

	public InventoryPieceItemHandler(ItemStackHandlerSync handler, int[] slotXs, int[] slotYs) {
		this(null, handler, slotXs, slotYs);
	}

	public InventoryPieceItemHandler(IOType type, ItemStackHandlerSync handler, int slotXs[], int[] slotYs) {
		super(type, handler, slotXs[0], slotYs[0], 44, 166, 18, 18, -1);
		this.getHandler().setEnclosing(this);
		this.slotXs = slotXs;
		this.slotYs = slotYs;
		// TODO Replace me with better and more dynamic texture handling
		if(IOType.OUTPUT.equals(type)) {
			this.textureX = 62;
			this.textureY = 166;
			this.width = this.height = 26;
			this.offset = -5;
		}
	}

	public InventoryPieceItemHandler(IOType type, int slotXs, int slotYs) {
		this(type, new ItemStackHandlerSync(1), new int[] { slotXs }, new int[] { slotYs });
	}

	public int getSlotPositionX(int slot) {
		return slotXs[slot];
	}

	public int getSlotPositionY(int slot) {
		return slotYs[slot];
	}
}
