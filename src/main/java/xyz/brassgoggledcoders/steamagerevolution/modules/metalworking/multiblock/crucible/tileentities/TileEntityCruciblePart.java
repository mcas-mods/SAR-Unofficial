package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.ControllerCrucible;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblockTile;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

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
