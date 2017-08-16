package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.tileentities;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityAlloyFurnaceController extends TileEntityAlloyFurnacePart {
	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}
}
