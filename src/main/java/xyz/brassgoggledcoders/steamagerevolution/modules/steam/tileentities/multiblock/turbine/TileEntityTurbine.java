package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.turbine;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.ITickableMultiblockPart;

public class TileEntityTurbine extends TileEntitySteamTurbinePart implements ITickableMultiblockPart {

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

	@Override
	public boolean tick(MultiblockControllerBase controller) {
		SteamTurbineController turbine = (SteamTurbineController) controller;
		boolean flag = false;

		for(TileEntitySteamInput tank : turbine.getInputs()) {

			if(tank.buffer.getFluidAmount() > SteamTurbineController.fluidTransferRate) {
				tank.buffer.drain(SteamTurbineController.fluidTransferRate, true);
				for(TileEntityMechanicalOutput output : turbine.getOutputs()) {

				}
				tank.markDirty();
				tank.sendBlockUpdate();
				this.markDirty();
				this.sendBlockUpdate();
				flag = true;
			}
		}

		return flag;
	}

}
