package xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMultiblockMachine;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.ControllerVat;

public abstract class TileEntityVatPart extends MultiblockInventoryTileEntity<ControllerVat> {

	@Override
	public Class<ControllerVat> getMultiblockControllerType() {
		return ControllerVat.class;
	}

	@Override
	public IMultiblockMachine getControllerInfo() {
		return new ControllerVat(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerVat(getWorld());
	}

}
