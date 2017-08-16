package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.tileentities;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityAlloyFurnaceSteamInput extends TileEntityAlloyFurnacePart {

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}

}
