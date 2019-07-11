package xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.SARMultiblockTileInventory;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.ControllerVat;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;

public abstract class TileEntityVatPart extends SARMultiblockTileInventory<ControllerVat> {

	@Override
	public Class<ControllerVat> getMultiblockControllerType() {
		return ControllerVat.class;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerVat(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerVat(getWorld());
	}

}
