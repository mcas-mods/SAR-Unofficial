package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

public interface IMultiblockControllerInfo {
	public String getName();

	// FIXME: Deduplicate these methods
	public int getMaximumXSize();

	public int getMaximumYSize();

	public int getMaximumZSize();

	public int getMinimumXSize();

	public int getMinimumYSize();

	public int getMinimumZSize();
}
