package xyz.brassgoggledcoders.steamagerevolution.api.semisolid;

import javax.annotation.Nullable;

public interface ISemisolidHolder {
	@Nullable
	SemisolidStack getCrushed();
	
	int getAmount();
	
	int getHolderCapacity();
	
	boolean fill(SemisolidStack material);
	
	boolean drain(ISemisolid type, int toDrain);
	
	@Deprecated
	void setInternal(SemisolidStack toSet);
}
