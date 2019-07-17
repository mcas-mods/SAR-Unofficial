package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMultiblockMachine;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.ControllerCrucible;

public abstract class TileEntityCruciblePart extends MultiblockInventoryTileEntity<ControllerCrucible> {

	@Override
	public Class<ControllerCrucible> getMultiblockControllerType() {
		return ControllerCrucible.class;
	}

	@Override
	public IMultiblockMachine getControllerInfo() {
		return new ControllerCrucible(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerCrucible(getWorld());
	}
}
