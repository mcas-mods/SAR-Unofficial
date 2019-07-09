package xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.SARMultiblockTileInventory;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.ControllerDistiller;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;

public abstract class TileEntityDistillerPart extends SARMultiblockTileInventory<ControllerDistiller> {

	@Override
	public Class<ControllerDistiller> getMultiblockControllerType() {
		return ControllerDistiller.class;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerDistiller(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerDistiller(getWorld());
	}

}
