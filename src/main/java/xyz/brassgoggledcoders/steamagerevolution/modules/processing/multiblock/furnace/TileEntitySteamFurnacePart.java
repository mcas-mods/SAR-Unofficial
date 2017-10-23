package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.TileEntityMultiblockBase;

public class TileEntitySteamFurnacePart extends TileEntityMultiblockBase<ControllerSteamFurnace> {
	public TileEntitySteamFurnacePart() {
		super(ControllerSteamFurnace.class, ControllerSteamFurnace::new);
	}
}
