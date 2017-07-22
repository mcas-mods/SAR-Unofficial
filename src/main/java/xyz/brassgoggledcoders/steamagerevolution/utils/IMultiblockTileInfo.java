package xyz.brassgoggledcoders.steamagerevolution.utils;

public interface IMultiblockTileInfo {
	public IMultiblockControllerInfo getControllerInfo();

	public boolean[] getValidPositions();

	public String getPartFunction();
}
