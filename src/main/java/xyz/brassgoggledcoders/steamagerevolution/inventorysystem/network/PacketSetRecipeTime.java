package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetRecipeTime implements IMessage {

	public BlockPos pos;
	public short maxTicks;

	public PacketSetRecipeTime() {
	}

	public PacketSetRecipeTime(BlockPos pos, short maxTicks) {
		this.pos = pos;
		this.maxTicks = maxTicks;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		maxTicks = buf.readShort();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeShort(maxTicks);
	}

}
