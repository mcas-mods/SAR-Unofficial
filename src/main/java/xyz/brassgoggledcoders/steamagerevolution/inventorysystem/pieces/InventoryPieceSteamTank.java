package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;

public class InventoryPieceSteamTank extends InventoryPieceFluidTank {

	public InventoryPieceSteamTank(IOType type, FluidTankSync handler, int xPosition, int yPosition) {
		super(type, handler, xPosition, yPosition, 0, 166, 22, 48, -3);
	}
}
