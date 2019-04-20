package xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial;

import net.minecraft.nbt.NBTTagCompound;

public class CrushedHandler implements ICrushedHandler {
	
	ICrushedHolder[] holders;
	
	public CrushedHandler(ICrushedHolder... holders) {
		this.holders = holders;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("size", holders.length);
		int i = 0;
		for(ICrushedHolder holder : holders) {
			data.setTag(Integer.toString(i), holder.getCrushed().serializeNBT());
		}
		return data;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		for(int i = 0; i < tag.getInteger("size"); i++) {
			holders[i].getCrushed().deserializeNBT((NBTTagCompound) tag.getTag(Integer.toString(i)));
		}
	}

	@Override
	public ICrushedHolder[] getHolders() {
		return holders;
	}

}
