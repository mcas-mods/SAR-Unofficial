package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityDrillFrame extends TileEntityDrillPart {
	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}
}
