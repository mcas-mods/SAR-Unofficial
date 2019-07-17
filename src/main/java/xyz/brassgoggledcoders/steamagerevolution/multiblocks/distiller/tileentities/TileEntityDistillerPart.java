package xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMultiblockMachine;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.ControllerDistiller;

public abstract class TileEntityDistillerPart extends MultiblockInventoryTileEntity<ControllerDistiller> {

	@Override
	public Class<ControllerDistiller> getMultiblockControllerType() {
		return ControllerDistiller.class;
	}

	@Override
	public IMultiblockMachine getControllerInfo() {
		return new ControllerDistiller(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerDistiller(getWorld());
	}

}
