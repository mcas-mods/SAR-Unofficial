package xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMultiblockMachine;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.ControllerSteelworks;

public abstract class TileEntitySteelworksPart extends MultiblockInventoryTileEntity<ControllerSteelworks> {

	@Override
	public Class<ControllerSteelworks> getMultiblockControllerType() {
		return ControllerSteelworks.class;
	}

	@Override
	public IMultiblockMachine getControllerInfo() {
		return new ControllerSteelworks(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerSteelworks(getWorld());
	}

}
