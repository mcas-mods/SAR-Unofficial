package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRequestStatusUpdate implements IMessage {

	BlockPos machineLocation;

	public PacketRequestStatusUpdate() {
	}

	public PacketRequestStatusUpdate(BlockPos machineLocation) {
		this.machineLocation = machineLocation;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		machineLocation = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(machineLocation.toLong());
	}

}
