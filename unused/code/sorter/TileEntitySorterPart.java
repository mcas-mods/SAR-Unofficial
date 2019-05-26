package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public class TileEntitySorterPart extends SARMultiblockTileInventory<ControllerSorter> {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return ControllerSorter.class;
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerSorter(getWorld());
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerSorter(null);
	}

}
