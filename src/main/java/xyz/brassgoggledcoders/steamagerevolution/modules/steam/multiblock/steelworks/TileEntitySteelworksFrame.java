package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.steelworks;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntitySteelworksFrame extends TileEntitySteelworksPart {

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

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}
}
