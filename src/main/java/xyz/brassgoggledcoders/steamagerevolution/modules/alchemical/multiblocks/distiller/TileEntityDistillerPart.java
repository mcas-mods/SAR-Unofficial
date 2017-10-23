package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.TileEntityMultiblockBase;

public class TileEntityDistillerPart extends TileEntityMultiblockBase<ControllerDistiller> {
	public TileEntityDistillerPart() {
		super(ControllerDistiller.class, ControllerDistiller::new);
	}
}