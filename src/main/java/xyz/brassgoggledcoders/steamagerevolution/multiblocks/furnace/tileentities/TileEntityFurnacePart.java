package xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMultiblockMachine;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace.ControllerSteamFurnace;

public abstract class TileEntityFurnacePart extends MultiblockInventoryTileEntity<ControllerSteamFurnace> {

	@Override
	public Class<ControllerSteamFurnace> getMultiblockControllerType() {
		return ControllerSteamFurnace.class;
	}

	@Override
	public IMultiblockMachine getControllerInfo() {
		return new ControllerSteamFurnace(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerSteamFurnace(getWorld());
	}

}