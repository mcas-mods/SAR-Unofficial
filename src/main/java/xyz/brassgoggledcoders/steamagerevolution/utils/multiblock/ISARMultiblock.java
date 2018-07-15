package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import xyz.brassgoggledcoders.steamagerevolution.utils.IMachine;

public interface ISARMultiblock extends IMachine {
	// TODO Duplicated in IHasInventory
	public String getName();

	public int getMaximumXSize();

	public int getMaximumYSize();

	public int getMaximumZSize();

	public int getMinimumXSize();

	public int getMinimumYSize();

	public int getMinimumZSize();
}
