package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

public interface ISARMultiblockTile {
    public ISARMultiblock getControllerInfo();

    public boolean[] getValidPositions();

    public default String getPartFunction() {
        return null;
    }
}
