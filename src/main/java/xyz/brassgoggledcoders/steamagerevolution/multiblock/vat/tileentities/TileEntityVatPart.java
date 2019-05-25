package xyz.brassgoggledcoders.steamagerevolution.multiblock.vat.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.multiblock.vat.ControllerVat;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

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
