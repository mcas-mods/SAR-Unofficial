package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetBoilerValue implements IMessage {

    public BlockPos pos;
    public int value;
    public boolean isTemp;

    public PacketSetBoilerValue() {
    }

    public PacketSetBoilerValue(BlockPos pos, int value, boolean isTemp) {
        this.pos = pos;
        this.value = value;
        this.isTemp = isTemp;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        value = buf.readInt();
        isTemp = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(value);
        buf.writeBoolean(isTemp);
    }

}
