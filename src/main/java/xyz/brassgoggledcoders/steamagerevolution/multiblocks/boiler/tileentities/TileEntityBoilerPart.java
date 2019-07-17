package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMultiblockMachine;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.ControllerBoiler;

public abstract class TileEntityBoilerPart extends MultiblockInventoryTileEntity<ControllerBoiler> {

	@Override
	public Class<ControllerBoiler> getMultiblockControllerType() {
		return ControllerBoiler.class;
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerBoiler(getWorld());
	}

	@Override
	public IMultiblockMachine getControllerInfo() {
		return new ControllerBoiler(null);
	}
}
