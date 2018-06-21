package xyz.brassgoggledcoders.steamagerevolution.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;

public class PacketMultiFluidUpdate implements IMessage {

	public BlockPos pos;
	public MultiFluidTank tank;
	public int id;

	public PacketMultiFluidUpdate() {}

	public PacketMultiFluidUpdate(BlockPos pos, MultiFluidTank tank, int id) {
		this.pos = pos;
		this.tank = tank;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		tank = new MultiFluidTank(0, null, id).readFromNBT(tag); // TODO
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());

		NBTTagCompound tag = new NBTTagCompound();
		if(tank != null) {
			tank.writeToNBT(tag);
		}
		ByteBufUtils.writeTag(buf, tag);
		buf.writeInt(id);
	}

}
