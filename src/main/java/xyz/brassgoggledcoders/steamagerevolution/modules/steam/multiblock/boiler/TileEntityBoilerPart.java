package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public abstract class TileEntityBoilerPart extends RectangularMultiblockTileEntityBase<ControllerBoiler> {

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
}
