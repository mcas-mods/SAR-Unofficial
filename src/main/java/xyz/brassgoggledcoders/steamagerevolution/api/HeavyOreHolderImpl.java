package xyz.brassgoggledcoders.steamagerevolution.api;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class HeavyOreHolderImpl implements IHeavyOreHolder {
	
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
	public void setOreLevel(String oreName, Integer oreLevel) {
		oreLevels.put(oreName, oreLevel);
	}
	
	@Override
	public HashMap<String, Integer> getMap() {
		return oreLevels;
	}
	
	public boolean transfer(IHeavyOreHolder to) {
		for(Entry<String, Integer> entry : oreLevels.entrySet()) {
			to.setOreLevel(entry.getKey(), to.getOreAmount(entry.getKey()) + 1);
			this.setOreLevel(entry.getKey(), this.getOreAmount(entry.getKey()) - 1);
			return true;
		}
		return false;
	}
	
	public static class Storage implements IStorage<IHeavyOreHolder> {

		@Override
		public NBTBase writeNBT(Capability<IHeavyOreHolder> capability, IHeavyOreHolder instance, EnumFacing side) {
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<IHeavyOreHolder> capability, IHeavyOreHolder instance, EnumFacing side, NBTBase nbt) {
			instance.deserializeNBT((NBTTagCompound)nbt);
		}
		
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
				setOreLevel(tag.getString("ore" + i), tag.getInteger("oreValue" + i));
		}
	}

}
