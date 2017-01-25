package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntitySteamInjector extends TileEntitySmelteryPart {

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}

}
