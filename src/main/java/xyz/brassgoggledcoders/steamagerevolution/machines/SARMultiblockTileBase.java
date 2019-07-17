package xyz.brassgoggledcoders.steamagerevolution.machines;

import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public abstract class SARMultiblockTileBase<T extends SARMultiblockBase> extends RectangularMultiblockTileEntityBase<T>
		implements IMultiblockMachineTile {

	@Override
	public IMultiblockMachine getControllerInfo() {
		return (IMultiblockMachine) IMachine.referenceMachinesList.get(this.getMultiblockController().getUID());
	}

	@Override
	public boolean[] getValidPositions() {
		return new boolean[] { isGoodForFrame(null), isGoodForSides(null), isGoodForTop(null), isGoodForBottom(null),
				isGoodForInterior(null) };
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public void onMachineActivated() {
	}

	@Override
	public void onMachineDeactivated() {
	}
}
