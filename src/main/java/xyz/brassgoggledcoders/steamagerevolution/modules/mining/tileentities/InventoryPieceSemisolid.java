package xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities;

import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece;

public class InventoryPieceSemisolid extends InventoryPiece {

	ISemisolidHandler handler;

	public InventoryPieceSemisolid(ISemisolidHandler holder, int xPos, int yPos) {
		this(holder, new int[] { xPos }, new int[] { yPos });
	}

	public InventoryPieceSemisolid(ISemisolidHandler holder, int[] xPos, int[] yPos) {
		super(xPos, yPos);
		this.handler = holder;
	}

	public ISemisolidHandler getHandler() {
		return handler;
	}

}
