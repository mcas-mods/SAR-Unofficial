package xyz.brassgoggledcoders.steamagerevolution;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public class CapabilityHandler {

	public static void init() {
		CapabilityManager.INSTANCE.register(ISpinHandler.class, new Capability.IStorage<ISpinHandler>() {
			@Override
			public NBTTagCompound writeNBT(Capability<ISpinHandler> capability, ISpinHandler instance,
					EnumFacing side) {
				return instance.serializeNBT();
			}

			@Override
			public void readNBT(Capability<ISpinHandler> capability, ISpinHandler instance, EnumFacing side,
					NBTBase nbt) {
				if(nbt instanceof NBTTagCompound)
					instance.deserializeNBT((NBTTagCompound) nbt);
			}
		}, SpinHandler.class);
	}
}
