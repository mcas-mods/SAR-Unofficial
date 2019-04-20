package xyz.brassgoggledcoders.steamagerevolution.api;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.nbt.NBTTagCompound;

public class HeavyOreHolder implements IHeavyOreHolder {
	
	public HashMap<String, Integer> oreLevels = Maps.newHashMap();

	@Override
	public int getOreAmount(String oreName) {
		return oreLevels.get(oreName);
	}

	@Override
	public boolean hasOre(String oreName) {
		return oreLevels.containsKey(oreName);
	}

	@Override
	public void setOreAmount(String oreName, Integer oreLevel) {
		oreLevels.put(oreName, oreLevel);
	}
	
	public boolean transfer(IHeavyOreHolder to) {
		for(Entry<String, Integer> entry : oreLevels.entrySet()) {
			to.setOreAmount(entry.getKey(), to.getOreAmount(entry.getKey()) + 1);
			this.setOreAmount(entry.getKey(), this.getOreAmount(entry.getKey()) - 1);
			return true;
		}
		return false;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("size", oreLevels.size());
		int i = 0;
		for(Entry<String, Integer> entry : oreLevels.entrySet()) {
			data.setString("ore" + i, entry.getKey());
			data.setInteger("oreValue" + i, entry.getValue());
			i++;
		}
		return data;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
			for(int i = 0; i < tag.getInteger("size"); i++) {
				setOreAmount(tag.getString("ore" + i), tag.getInteger("oreValue" + i));
		}
	}

}
