package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler;

import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;

public class TileEntitySolidFirebox extends TileEntityBasicBoilerPart {

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

}
