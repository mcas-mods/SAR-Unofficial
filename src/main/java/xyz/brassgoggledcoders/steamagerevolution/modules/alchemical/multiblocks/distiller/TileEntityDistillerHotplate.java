package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityDistillerHotplate extends TileEntityDistillerPart<ControllerDistiller> {

	public TileEntityDistillerHotplate() {
		super(ControllerDistiller.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

}
