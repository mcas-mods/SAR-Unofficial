package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.ControllerAlloyFurnace;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.IMultiblockControllerInfo;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public abstract class TileEntityAlloyFurnacePart extends SARMultiblockTileInventory<ControllerAlloyFurnace> {

	@Override
	public Class<ControllerAlloyFurnace> getMultiblockControllerType() {
		return ControllerAlloyFurnace.class;
	}

	@Override
	public IMultiblockControllerInfo getControllerInfo() {
		return new ControllerAlloyFurnace(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerAlloyFurnace(getWorld());
	}

}
