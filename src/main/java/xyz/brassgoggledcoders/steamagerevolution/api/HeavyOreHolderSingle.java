package xyz.brassgoggledcoders.steamagerevolution.api;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class HeavyOreHolderSingle implements IHeavyOreHolder {
	
	public String oreName;
	public int oreLevel;

	@Override
	public int getOreAmount(String oreName) {
		return oreLevel;
	}

	@Override
	public boolean hasOre(String oreName) {
		return oreName.equalsIgnoreCase(this.oreName);
	}

	@Override
	public void setOreLevel(String oreName, Integer oreLevel) {
		this.oreName = oreName;
		this.oreLevel = oreLevel;
	}
	
	public boolean transfer(IHeavyOreHolder to) {
		to.setOreLevel(oreName, to.getOreAmount(oreName) + 1);
		this.setOreLevel(oreName, oreLevel - 1);
		return true;
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
			data.setString("ore", oreName);
			data.setInteger("oreValue", oreLevel);
		return data;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		setOreLevel(tag.getString("ore"), tag.getInteger("oreValue"));
	}

}
