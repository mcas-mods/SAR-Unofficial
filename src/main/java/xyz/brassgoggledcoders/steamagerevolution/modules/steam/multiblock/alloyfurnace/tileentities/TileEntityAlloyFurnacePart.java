package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.ControllerAlloyFurnace;

public abstract class TileEntityAlloyFurnacePart extends RectangularMultiblockTileEntityBase<ControllerAlloyFurnace> {

	@Override
	public Class<ControllerAlloyFurnace> getMultiblockControllerType() {
		return ControllerAlloyFurnace.class;
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
		return new ControllerAlloyFurnace(getWorld());
	}

}
