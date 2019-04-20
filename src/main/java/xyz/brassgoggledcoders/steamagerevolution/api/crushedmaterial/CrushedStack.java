package xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class CrushedStack implements INBTSerializable<NBTTagCompound> {
	ICrushedMaterial material;
	int amount;
	
	public CrushedStack(ICrushedMaterial material, int amount) {
		this.material = material;
		this.amount = amount;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("name", material.getRegistryName().toString());
		tag.setInteger("amount", amount);
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.material = SteamAgeRevolution.materialRegistry.getEntry(new ResourceLocation(nbt.getString("name")));
		this.amount = nbt.getInteger("amount");
	}
}
