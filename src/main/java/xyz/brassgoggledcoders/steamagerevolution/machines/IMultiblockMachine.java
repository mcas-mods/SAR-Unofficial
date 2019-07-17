package xyz.brassgoggledcoders.steamagerevolution.machines;

public interface IMultiblockMachine extends IMachine {
	public int getMaximumXSize();

	public int getMaximumYSize();

	public int getMaximumZSize();

	public int getMinimumXSize();

	public int getMinimumYSize();

	public int getMinimumZSize();
}
