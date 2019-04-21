package xyz.brassgoggledcoders.steamagerevolution.api.semisolid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISemisolidHandler extends INBTSerializable<NBTTagCompound> {
	@Deprecated
	public ISemisolidHolder[] getHolders();
	
	boolean fill(SemisolidStack material);
	
	boolean drain(ISemisolid type, int toDrain);
	
	default void onContentsChanged() {
		
	}
}
