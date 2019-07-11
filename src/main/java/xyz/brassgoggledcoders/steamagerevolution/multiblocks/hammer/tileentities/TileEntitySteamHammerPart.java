package xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventoryTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.ControllerSteamHammer;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;

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
	public ISARMultiblock getControllerInfo() {
		return new ControllerSteamHammer(null);
	}
}
