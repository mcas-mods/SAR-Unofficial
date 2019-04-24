package xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityGrinderFrame extends TileEntityGrinderPart {
	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}
	
	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}
	
	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}
}
