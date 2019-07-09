package xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.SARMultiblockTileInventory;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace.ControllerSteamFurnace;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;

public abstract class TileEntityFurnacePart extends SARMultiblockTileInventory<ControllerSteamFurnace> {

	@Override
	public Class<ControllerSteamFurnace> getMultiblockControllerType() {
		return ControllerSteamFurnace.class;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerSteamFurnace(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerSteamFurnace(getWorld());
	}

}