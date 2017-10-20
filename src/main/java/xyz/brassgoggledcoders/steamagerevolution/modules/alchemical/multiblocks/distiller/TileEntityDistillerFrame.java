package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityDistillerFrame extends TileEntityDistillerPart<ControllerDistiller> {

	public TileEntityDistillerFrame() {
		super(ControllerDistiller.class);
		// TODO Auto-generated constructor stub
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
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}
}
