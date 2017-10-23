package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.TileEntityMultiblockBase;

public class TileEntityCruciblePart extends TileEntityMultiblockBase<ControllerCrucible> {
	public TileEntityCruciblePart() {
		super(ControllerCrucible.class, ControllerCrucible::new);
	}
}
