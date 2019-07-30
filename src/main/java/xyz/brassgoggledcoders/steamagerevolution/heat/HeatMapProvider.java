package xyz.brassgoggledcoders.steamagerevolution.heat;

import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class HeatMapProvider implements ICapabilitySerializable<NBTTagByteArray> {

    private final IHeatMap map;

    public HeatMapProvider(Chunk chunk) {
        map = new HeatMapHolder(chunk);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTTagByteArray serializeNBT() {
        return map.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagByteArray nbt) {
        map.deserializeNBT(nbt);
    }

}
