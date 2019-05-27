package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

public enum IOTYPE {
	INPUT(0), OUTPUT(1), STEAM(-1), UNDEFINED(Integer.MIN_VALUE);

	final int networkID;

	IOTYPE(int networkID) {
		this.networkID = networkID;
	}

	public static int getNetworkID(IOTYPE type) {
		return type.networkID;
	}

	public static IOTYPE getTypeFromID(int id) {
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
