package xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityTankWindow extends TileEntityTankPart {

	public TileEntityTankWindow() {

	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
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
