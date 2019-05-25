package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public abstract class TileEntityGrinderPart extends SARMultiblockTileInventory<ControllerGrinder> {

	@Override
	public Class<ControllerGrinder> getMultiblockControllerType() {
		return ControllerGrinder.class;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerGrinder(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerGrinder(getWorld());
	}
}