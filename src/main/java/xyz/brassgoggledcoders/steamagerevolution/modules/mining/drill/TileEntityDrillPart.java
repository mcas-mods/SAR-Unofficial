package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public abstract class TileEntityDrillPart extends SARMultiblockTileInventory<ControllerDrill> {

	@Override
	public Class<ControllerDrill> getMultiblockControllerType() {
		return ControllerDrill.class;
	}

	@Override
	public ISARMultiblock getControllerInfo() {
		return new ControllerDrill(null);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerDrill(getWorld());
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}
	
	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}
	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}
	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}
}