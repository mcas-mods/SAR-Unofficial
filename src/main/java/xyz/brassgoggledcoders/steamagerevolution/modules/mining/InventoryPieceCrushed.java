package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.ICrushedHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece;

public class InventoryPieceCrushed extends InventoryPiece {
	
	ICrushedHandler handler;

	public InventoryPieceCrushed(ICrushedHandler holder, int xPos, int yPos) {
		this(holder, new int[] {xPos}, new int[] {yPos});
	}
	
	public InventoryPieceCrushed(ICrushedHandler holder, int[] xPos, int[] yPos) {
		super(xPos, yPos);
		this.handler = holder;
	}
	
	public ICrushedHandler getHandler() {
		return handler;
	}

}
