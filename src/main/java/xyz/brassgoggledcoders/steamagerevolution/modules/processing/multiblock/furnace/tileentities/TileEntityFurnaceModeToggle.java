package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.tileentities;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityFurnaceModeToggle extends TileEntityFurnacePart {
	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}
}
