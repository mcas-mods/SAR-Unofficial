package xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileBase;

public abstract class TileEntityTankPart extends SARMultiblockTileBase<ControllerTank> {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return ControllerTank.class;
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerTank(getWorld());
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerTank(null);
	}
}
