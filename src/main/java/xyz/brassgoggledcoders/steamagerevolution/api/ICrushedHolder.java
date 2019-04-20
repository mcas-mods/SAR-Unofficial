package xyz.brassgoggledcoders.steamagerevolution.api;

import javax.annotation.Nullable;

public interface ICrushedHolder {
	@Nullable
	CrushedStack getCrushed();
	
	int getAmount();
	
	int getHolderCapacity();
	
	boolean fill(CrushedStack material);
	
	boolean drain(ICrushedMaterial type, int toDrain);
}
