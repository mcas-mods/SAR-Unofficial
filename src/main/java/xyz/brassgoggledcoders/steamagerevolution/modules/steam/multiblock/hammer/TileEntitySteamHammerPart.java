package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntitySteamHammerPart extends RectangularMultiblockTileEntityBase<ControllerSteamHammer> {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		// TODO Auto-generated method stub
		return ControllerSteamHammer.class;
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

}
