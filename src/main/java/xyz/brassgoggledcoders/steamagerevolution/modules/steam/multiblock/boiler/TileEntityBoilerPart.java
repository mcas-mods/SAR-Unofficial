package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.TileEntityMultiblockBase;

public class TileEntityBoilerPart extends TileEntityMultiblockBase<ControllerBoiler> {
	public TileEntityBoilerPart() {
		super(ControllerBoiler.class, ControllerBoiler::new);
	}
}
