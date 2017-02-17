package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntitySteamHammerHammer extends TileEntitySteamHammerPart {
	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}
}
