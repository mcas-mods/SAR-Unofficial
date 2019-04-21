package xyz.brassgoggledcoders.steamagerevolution.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSemisolidCart implements IMessage {

	public int id;
	public NBTTagCompound tag;

	public PacketSemisolidCart() {
	}

	public PacketSemisolidCart(int id, NBTTagCompound tag) {
		this.id = id;
		this.tag = tag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		ByteBufUtils.writeTag(buf, tag);
	}

}
