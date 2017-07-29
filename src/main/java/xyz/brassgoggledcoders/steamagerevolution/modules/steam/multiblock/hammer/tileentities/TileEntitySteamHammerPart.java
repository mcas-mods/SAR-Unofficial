package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.ControllerSteamHammer;
import xyz.brassgoggledcoders.steamagerevolution.utils.IMultiblockControllerInfo;
import xyz.brassgoggledcoders.steamagerevolution.utils.IMultiblockTileInfo;

public class TileEntitySteamHammerPart extends RectangularMultiblockTileEntityBase<ControllerSteamHammer>
		implements IMultiblockTileInfo {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		// TODO Auto-generated method stub
		return ControllerSteamHammer.class;
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
		return true;
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
		// TODO Auto-generated method stub
		return new ControllerSteamHammer(getWorld());
	}

	@Override
	public boolean[] getValidPositions() {
		return new boolean[] {isGoodForFrame(null), isGoodForSides(null), isGoodForTop(null), isGoodForBottom(null),
				isGoodForInterior(null)};
	}

	@Override
	public IMultiblockControllerInfo getControllerInfo() {
		return new ControllerSteamHammer(null);
	}

}
