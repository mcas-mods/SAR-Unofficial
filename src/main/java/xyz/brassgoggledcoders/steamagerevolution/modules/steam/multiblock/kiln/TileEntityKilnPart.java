package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.kiln;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import xyz.brassgoggledcoders.steamagerevolution.utils.IMultiblockControllerInfo;
import xyz.brassgoggledcoders.steamagerevolution.utils.IMultiblockTileInfo;

public abstract class TileEntityKilnPart extends RectangularMultiblockTileEntityBase<ControllerKiln>
		implements IMultiblockTileInfo {

	@Override
	public Class<ControllerKiln> getMultiblockControllerType() {
		return ControllerKiln.class;
	}

	@Override
	public IMultiblockControllerInfo getControllerInfo() {
		return new ControllerKiln(null);
	}

	@Override
	public boolean[] getValidPositions() {
		return new boolean[] {isGoodForFrame(null), isGoodForSides(null), isGoodForTop(null), isGoodForBottom(null),
				isGoodForInterior(null)};
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
	public void onMachineActivated() {}

	@Override
	public void onMachineDeactivated() {}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerKiln(getWorld());
	}

}
