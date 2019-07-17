package xyz.brassgoggledcoders.steamagerevolution.machines;

public interface IMultiblockMachineTile {
	public IMultiblockMachine getControllerInfo();

	public boolean[] getValidPositions();

	public default String getPartFunction() {
		return null;
	}
}
