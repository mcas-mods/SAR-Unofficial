package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.turbine;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public abstract class TileEntitySteamTurbinePart extends RectangularMultiblockTileEntityBase {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return SteamTurbineController.class;
	}

	@Override
	public void onMachineActivated() {}

	@Override
	public void onMachineDeactivated() {}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new SteamTurbineController(getWorld());
	}

	// TODO Localisation
	public abstract String getPartName();

}
