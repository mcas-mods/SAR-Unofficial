package xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.SARMultiblockTileInventory;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.ControllerAlloyFurnace;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;

public abstract class TileEntityAlloyFurnacePart extends SARMultiblockTileInventory<ControllerAlloyFurnace> {

	@Override
	public Class<ControllerAlloyFurnace> getMultiblockControllerType() {
		return ControllerAlloyFurnace.class;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerAlloyFurnace(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerAlloyFurnace(getWorld());
	}

}
