package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.SARMultiblockTileInventory;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.ControllerCrucible;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;

public abstract class TileEntityCruciblePart extends SARMultiblockTileInventory<ControllerCrucible> {

	@Override
	public Class<ControllerCrucible> getMultiblockControllerType() {
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
