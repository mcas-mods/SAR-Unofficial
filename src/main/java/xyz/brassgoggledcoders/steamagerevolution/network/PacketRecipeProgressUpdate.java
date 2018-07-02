package xyz.brassgoggledcoders.steamagerevolution.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRecipeProgressUpdate implements IMessage {

	public BlockPos pos;
	public int currentRecipeMaxTicks;

	public PacketRecipeProgressUpdate() {
	}

	public PacketRecipeProgressUpdate(int maxTicks, BlockPos pos) {
		this.currentRecipeMaxTicks = maxTicks;
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		currentRecipeMaxTicks = buf.readInt();
		pos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(currentRecipeMaxTicks);
		buf.writeLong(pos.toLong());
	}

}
