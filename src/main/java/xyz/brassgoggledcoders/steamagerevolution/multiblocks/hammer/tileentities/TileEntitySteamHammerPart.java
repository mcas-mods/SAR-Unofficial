package xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMultiblockMachine;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.ControllerSteamHammer;

public class TileEntitySteamHammerPart extends MultiblockInventoryTileEntity<ControllerSteamHammer> {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return ControllerSteamHammer.class;
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		// TODO Auto-generated method stub
		return new ControllerSteamHammer(getWorld());
	}

	@Override
	public IMultiblockMachine getControllerInfo() {
		return new ControllerSteamHammer(null);
	}
}
