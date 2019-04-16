package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.ControllerSteamFurnace;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public abstract class TileEntityDrillPart extends SARMultiblockTileInventory<ControllerDrill> {

	@Override
	public Class<ControllerDrill> getMultiblockControllerType() {
		return ControllerDrill.class;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerDrill(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerDrill(getWorld());
	}

}