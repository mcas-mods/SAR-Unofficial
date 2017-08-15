package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.kiln;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityKilnDoor extends TileEntityKilnPart {

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return false;
	}
}
