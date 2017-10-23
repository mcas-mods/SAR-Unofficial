package xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.TileEntityMultiblockBase;

public class TileEntityTankPart extends TileEntityMultiblockBase<ControllerTank> {
	public TileEntityTankPart() {
		super(ControllerTank.class, ControllerTank::new);
	}
}
