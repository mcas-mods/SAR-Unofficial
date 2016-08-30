package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler;

import xyz.brassgoggledcoders.boilerplate.multiblock.MultiblockControllerBase;
import xyz.brassgoggledcoders.boilerplate.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public abstract class TileEntityBasicBoilerPart extends RectangularMultiblockTileEntityBase {

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
