package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.io;

public abstract class IO {
	IOType type;

	public IO(IOType type) {
		this.type = type;
	}

	public IOType getType() {
		return type;
	}
}
