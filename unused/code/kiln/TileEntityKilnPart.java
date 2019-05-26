package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.kiln;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public abstract class TileEntityKilnPart extends SARMultiblockTileInventory<ControllerKiln> {

	@Override
	public Class<ControllerKiln> getMultiblockControllerType() {
		return ControllerKiln.class;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerKiln(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerKiln(getWorld());
	}

}
