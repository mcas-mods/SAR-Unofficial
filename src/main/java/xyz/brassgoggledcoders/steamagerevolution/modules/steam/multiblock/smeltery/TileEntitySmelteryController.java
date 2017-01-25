package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntitySmelteryController extends TileEntitySmelteryPart {
	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}
}
