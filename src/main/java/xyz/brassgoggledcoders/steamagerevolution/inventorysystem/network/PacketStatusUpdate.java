package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketStatusUpdate implements IMessage {

	BlockPos machineLocation;
	int currentProgress;
	short errorID;

	public PacketStatusUpdate() {
	}

	public PacketStatusUpdate(BlockPos machineLocation, int currentProgress, int errorID) {
		this.machineLocation = machineLocation;
		this.currentProgress = currentProgress;
		this.errorID = Integer.valueOf(errorID).shortValue();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		machineLocation = BlockPos.fromLong(buf.readLong());
		currentProgress = buf.readInt();
		errorID = buf.readShort();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(machineLocation.toLong());
		buf.writeInt(currentProgress);
		buf.writeShort(errorID);
	}

}
