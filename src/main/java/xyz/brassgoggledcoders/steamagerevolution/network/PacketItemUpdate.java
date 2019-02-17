package xyz.brassgoggledcoders.steamagerevolution.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketItemUpdate implements IMessage {

	public BlockPos pos;
	public ItemStack item;
	public int slot;

	@Deprecated
	public PacketItemUpdate() {
	}

	public PacketItemUpdate(BlockPos pos, ItemStack item, int slot) {
		this.pos = pos;
		this.item = item;
		this.slot = slot;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		item = new ItemStack(tag);
		slot = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		NBTTagCompound tag = new NBTTagCompound();
		item.writeToNBT(tag);
		ByteBufUtils.writeTag(buf, tag);
		buf.writeInt(slot);
	}

}
