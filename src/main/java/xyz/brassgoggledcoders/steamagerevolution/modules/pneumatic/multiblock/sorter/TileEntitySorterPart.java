package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.multiblock.sorter;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntitySorterPart extends RectangularMultiblockTileEntityBase<ControllerSorter> {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return ControllerSorter.class;
	}

	@Override
	public void onMachineAssembled(MultiblockControllerBase multiblockControllerBase) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMachineBroken() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMachineActivated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMachineDeactivated() {
		// TODO Auto-generated method stub

	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerSorter(this.getWorld());
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
