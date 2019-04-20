package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import xyz.brassgoggledcoders.steamagerevolution.api.IHeavyOreHolder;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece;

public class InventoryPieceOre extends InventoryPiece {
	
	IHeavyOreHolder holder;

	public InventoryPieceOre(IHeavyOreHolder holder, int xPos, int yPos) {
		this(holder, new int[] {xPos}, new int[] {yPos});
	}
	
	public InventoryPieceOre(IHeavyOreHolder holder, int[] xPos, int[] yPos) {
		super(xPos, yPos);
		this.holder = holder;
	}
	
	public IHeavyOreHolder getOreHolder() {
		return holder;
	}

}
