package xyz.brassgoggledcoders.steamagerevolution.heat;

import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHeatMap extends INBTSerializable<NBTTagByteArray> {
    public void setHeatAt(BlockPos pos, int heat);

    public int getHeatAt(BlockPos pos);
}
