package xyz.brassgoggledcoders.steamagerevolution.heat;

import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;

public class HeatMapHolder implements IHeatMap {
    private Chunk containingChunk;
    private NibbleArray blockHeat;

    public HeatMapHolder(Chunk chunk) {
        this.containingChunk = chunk;
        this.blockHeat = new NibbleArray();
    }

    public static void calculateHeat(World world, Chunk chunk, BlockPos newSource) {
        IHeatMap map = chunk.getCapability(null, null);
        if(chunk.isLoaded() && world.isAreaLoaded(newSource, 8)) {
            AxisAlignedBB temp = new AxisAlignedBB(newSource).grow(8);
            for(BlockPos pos : BlockPos.getAllInBoxMutable((int) temp.minX, (int) temp.minY, (int) temp.minZ,
                    (int) temp.maxX, (int) temp.maxY, (int) temp.maxZ)) {

            }
        }
    }

    @Override
    public int getHeatAt(BlockPos pos) {
        return blockHeat.get(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void setHeatAt(BlockPos pos, int heat) {
        blockHeat.set(pos.getX(), pos.getY(), pos.getZ(), heat);
        containingChunk.markDirty();
    }

    @Override
    public NBTTagByteArray serializeNBT() {
        return new NBTTagByteArray(blockHeat.getData());
    }

    @Override
    public void deserializeNBT(NBTTagByteArray nbt) {
        blockHeat = new NibbleArray(nbt.getByteArray());
    }

}
