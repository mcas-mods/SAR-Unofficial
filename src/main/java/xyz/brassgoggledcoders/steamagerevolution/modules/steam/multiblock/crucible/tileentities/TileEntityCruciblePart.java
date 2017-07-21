package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;

import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.ControllerCrucible;

public abstract class TileEntityCruciblePart extends RectangularMultiblockTileEntityBase<ControllerCrucible> {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return ControllerCrucible.class;
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
