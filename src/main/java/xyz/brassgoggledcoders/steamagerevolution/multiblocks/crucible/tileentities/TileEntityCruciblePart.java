package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.SARMultiblockTileInventory;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.ControllerCrucible;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.*;

public abstract class TileEntityCruciblePart extends SARMultiblockTileInventory<ControllerCrucible>
		implements ISARMultiblockTile {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return ControllerCrucible.class;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerCrucible(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerCrucible(getWorld());
	}
}
