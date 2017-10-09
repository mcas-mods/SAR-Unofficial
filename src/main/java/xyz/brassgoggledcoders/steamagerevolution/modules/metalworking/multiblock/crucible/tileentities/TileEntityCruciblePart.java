package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;

import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.ControllerCrucible;
import xyz.brassgoggledcoders.steamagerevolution.utils.IMultiblockControllerInfo;
import xyz.brassgoggledcoders.steamagerevolution.utils.IMultiblockTileInfo;

public abstract class TileEntityCruciblePart extends RectangularMultiblockTileEntityBase<ControllerCrucible>
		implements IMultiblockTileInfo {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return ControllerCrucible.class;
	}

	@Override
	public boolean[] getValidPositions() {
		return new boolean[] {isGoodForFrame(null), isGoodForSides(null), isGoodForTop(null), isGoodForBottom(null),
				isGoodForInterior(null)};
	}

	@Override
	public IMultiblockControllerInfo getControllerInfo() {
		return new ControllerCrucible(null);
	}

	@Override
	public void onMachineActivated() {}

	@Override
	public void onMachineDeactivated() {}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerCrucible(getWorld());
	}
}
