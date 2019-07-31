package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetTemperature implements IMessage {

    public BlockPos pos;
    public int temp;

    public PacketSetTemperature() {
    }

    public PacketSetTemperature(BlockPos pos, int value) {
        this.pos = pos;
        this.temp = value;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        temp = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(temp);
    }

}
