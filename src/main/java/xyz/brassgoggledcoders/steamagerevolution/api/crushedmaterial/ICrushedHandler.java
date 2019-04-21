package xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICrushedHandler extends INBTSerializable<NBTTagCompound> {
	@Deprecated
	public ICrushedHolder[] getHolders();
	
	boolean fill(CrushedStack material);
	
	boolean drain(ICrushedMaterial type, int toDrain);
}
