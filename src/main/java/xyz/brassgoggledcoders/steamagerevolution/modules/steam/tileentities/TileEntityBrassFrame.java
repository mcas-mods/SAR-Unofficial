package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;

public class TileEntityBrassFrame extends TileEntityBasicBoilerPart {

	public TileEntityBrassFrame() {

	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {

		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {

		return true;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {

		return true;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {

		return true;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {

		return false;
	}

}
