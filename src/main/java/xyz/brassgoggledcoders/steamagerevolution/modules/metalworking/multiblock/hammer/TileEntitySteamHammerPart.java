package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.TileEntityMultiblockBase;

public class TileEntitySteamHammerPart extends TileEntityMultiblockBase<ControllerSteamHammer> {
	public TileEntitySteamHammerPart() {
		super(ControllerSteamHammer.class, ControllerSteamHammer::new);
	}
}
