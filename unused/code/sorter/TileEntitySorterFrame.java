package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntitySorterFrame extends TileEntitySorterPart {
	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return true;
	}
}
