package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.turbine;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityMechanicalOutput extends TileEntitySteamTurbinePart {

	@Override
	public String getPartName() {
		return "Mechanical Output";
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
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
