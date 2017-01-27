package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public abstract class TileEntityBasicBoilerPart extends RectangularMultiblockTileEntityBase<BasicBoilerController> {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return BasicBoilerController.class;
	}

	@Override
	public void onMachineActivated() {}

	@Override
	public void onMachineDeactivated() {}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new BasicBoilerController(getWorld());
	}
}
