package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;

import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.ControllerBoiler;
import xyz.brassgoggledcoders.steamagerevolution.utils.IMultiblockControllerInfo;
import xyz.brassgoggledcoders.steamagerevolution.utils.IMultiblockTileInfo;

public abstract class TileEntityBoilerPart extends RectangularMultiblockTileEntityBase<ControllerBoiler>
		implements IMultiblockTileInfo {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return ControllerBoiler.class;
	}

	@Override
	public void onMachineActivated() {}

	@Override
	public void onMachineDeactivated() {}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerBoiler(getWorld());
	}

	@Override
	public IMultiblockControllerInfo getControllerInfo() {
		return new ControllerBoiler(null);
	}

	@Override
	public boolean[] getValidPositions() {
		return new boolean[] {isGoodForFrame(null), isGoodForSides(null), isGoodForTop(null), isGoodForBottom(null),
				isGoodForInterior(null)};
	}
}
