package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

public interface IMultiblockTileInfo {
	public IMultiblockControllerInfo getControllerInfo();

	public boolean[] getValidPositions();

	public default String getPartFunction() {
		return null;
	}
}
