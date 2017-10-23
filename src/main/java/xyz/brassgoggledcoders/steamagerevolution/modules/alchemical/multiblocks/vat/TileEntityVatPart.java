package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.TileEntityMultiblockBase;

public class TileEntityVatPart extends TileEntityMultiblockBase<ControllerVat> {
	public TileEntityVatPart() {
		super(ControllerVat.class, ControllerVat::new);
	}
}
