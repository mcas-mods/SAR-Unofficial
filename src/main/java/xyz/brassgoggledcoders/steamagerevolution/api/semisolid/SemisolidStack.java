package xyz.brassgoggledcoders.steamagerevolution.api.semisolid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class SemisolidStack implements INBTSerializable<NBTTagCompound> {
	ISemisolid material;
	public int amount;
	
	public SemisolidStack(ISemisolid material, int amount) {
		this.material = material;
		this.amount = amount;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("name", getMaterial().getRegistryName().toString());
		tag.setInteger("amount", amount);
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(!nbt.isEmpty()) {
			this.material = SteamAgeRevolution.semisolidRegistry.getEntry(new ResourceLocation(nbt.getString("name")));
			this.amount = nbt.getInteger("amount");
		}
	}

	public ISemisolid getMaterial() {
		return material;
	}
}
