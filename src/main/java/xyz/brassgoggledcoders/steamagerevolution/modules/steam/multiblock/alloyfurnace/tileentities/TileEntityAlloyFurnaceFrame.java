package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.ControllerAlloyFurnace;

public class TileEntityAlloyFurnaceFrame extends TileEntityAlloyFurnacePart {

	@Override
	public void onMachineAssembled(MultiblockControllerBase multiblockControllerBase) {
		ControllerAlloyFurnace controller = (ControllerAlloyFurnace) multiblockControllerBase;
		controller.isHardened = false;
	}

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

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}
}
