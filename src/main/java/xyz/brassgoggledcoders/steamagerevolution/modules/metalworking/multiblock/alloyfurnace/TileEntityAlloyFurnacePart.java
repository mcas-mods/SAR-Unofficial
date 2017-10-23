package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.TileEntityMultiblockBase;

public class TileEntityAlloyFurnacePart extends TileEntityMultiblockBase<ControllerAlloyFurnace> {
	public TileEntityAlloyFurnacePart() {
		super(ControllerAlloyFurnace.class, ControllerAlloyFurnace::new);
	}
}
