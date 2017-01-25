package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.turbine;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityTurbineFrame extends TileEntitySteamTurbinePart {

	@Override
	public String getPartName() {
		return "Turbine Frame";
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
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
