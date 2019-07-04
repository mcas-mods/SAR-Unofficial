package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

public enum IOType {
	INPUT(0), OUTPUT(1), POWER(-1);

	int networkID;

	IOType(int networkID) {
		this.networkID = networkID;
	}
}
