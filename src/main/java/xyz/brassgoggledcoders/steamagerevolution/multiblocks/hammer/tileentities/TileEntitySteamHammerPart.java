package xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.ControllerSteamHammer;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public class TileEntitySteamHammerPart extends SARMultiblockTileInventory<ControllerSteamHammer> {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return ControllerSteamHammer.class;
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		// TODO Auto-generated method stub
		return new ControllerSteamHammer(getWorld());
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerSteamHammer(null);
	}
}
