package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

public enum TankType {
    INPUT(0), OUTPUT(1), STEAM(-1), UNDEFINED(Integer.MIN_VALUE);

    final int networkID;

    TankType(int networkID) {
        this.networkID = networkID;
    }

    public static int getNetworkID(TankType type) {
        return type.networkID;
    }

    public static TankType getTypeFromID(int id) {
        switch (id) {
            case 0:
                return INPUT;
            case 1:
                return OUTPUT;
            case -1:
                return STEAM;
            default:
                return UNDEFINED;
        }
    }
}
