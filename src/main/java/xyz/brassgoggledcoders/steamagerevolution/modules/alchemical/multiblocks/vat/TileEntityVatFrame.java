package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityVatFrame extends TileEntityVatPart {

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}
}
