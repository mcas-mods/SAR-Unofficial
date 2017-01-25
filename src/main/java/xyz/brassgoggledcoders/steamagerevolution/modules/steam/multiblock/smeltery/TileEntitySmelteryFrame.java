package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntitySmelteryFrame extends TileEntitySmelteryPart {
	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}
}
