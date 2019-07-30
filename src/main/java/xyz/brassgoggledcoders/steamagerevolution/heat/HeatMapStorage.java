package xyz.brassgoggledcoders.steamagerevolution.heat;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class HeatMapStorage implements IStorage<IHeatMap> {

    @Override
    public NBTBase writeNBT(Capability<IHeatMap> capability, IHeatMap instance, EnumFacing side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IHeatMap> capability, IHeatMap instance, EnumFacing side, NBTBase nbt) {
        instance.deserializeNBT((NBTTagByteArray) nbt);
    }

}
