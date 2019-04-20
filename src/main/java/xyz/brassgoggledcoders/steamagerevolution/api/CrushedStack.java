package xyz.brassgoggledcoders.steamagerevolution.api;

public class CrushedStack {
	ICrushedMaterial material;
	int amount;
	
	public CrushedStack(ICrushedMaterial material, int amount) {
		this.material = material;
		this.amount = amount;
	}
}
