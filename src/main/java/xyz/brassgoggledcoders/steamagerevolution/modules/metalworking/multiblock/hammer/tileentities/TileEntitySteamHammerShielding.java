package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.tileentities;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntitySteamHammerShielding extends TileEntitySteamHammerPart {
	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}
}
