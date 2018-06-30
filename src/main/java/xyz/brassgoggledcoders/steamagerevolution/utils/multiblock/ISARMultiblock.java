package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

public interface ISARMultiblock {
	// TODO Duplicated in IHasInventory
	public String getName();

	public int getMaximumXSize();

	public int getMaximumYSize();

	public int getMaximumZSize();

	public int getMinimumXSize();

	public int getMinimumYSize();

	public int getMinimumZSize();
}
