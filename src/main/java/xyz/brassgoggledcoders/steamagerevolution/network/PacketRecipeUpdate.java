package xyz.brassgoggledcoders.steamagerevolution.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRecipeUpdate implements IMessage {

	public int id;
	public BlockPos pos;
	public String type;

	public PacketRecipeUpdate() {
	}

	public PacketRecipeUpdate(int id, BlockPos pos, String type) {
		this.id = id;
		this.pos = pos;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		pos = BlockPos.fromLong(buf.readLong());
		type = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeUTF8String(buf, type);
	}

}
