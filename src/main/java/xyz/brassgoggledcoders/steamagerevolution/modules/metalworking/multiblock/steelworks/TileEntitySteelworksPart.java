package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.TileEntityMultiblockBase;

public class TileEntitySteelworksPart extends TileEntityMultiblockBase<ControllerSteelworks> {
	public TileEntitySteelworksPart() {
		super(ControllerSteelworks.class, ControllerSteelworks::new);
	}
}
