package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.multiblock.sorter;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;

public class TileEntitySorterPart extends MultiblockTileEntityBase<ControllerSorter> {

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

}
