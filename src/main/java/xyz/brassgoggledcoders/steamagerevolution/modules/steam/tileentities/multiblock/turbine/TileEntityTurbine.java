package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.turbine;

import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;

public class TileEntityTurbine extends TileEntitySteamTurbinePart {

	@Override
	public String getPartName() {
		return "Turbine";
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
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
		return false;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

}
