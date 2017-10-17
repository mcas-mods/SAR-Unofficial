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

	public PacketMultiFluidUpdate() {}

	public PacketMultiFluidUpdate(BlockPos pos, MultiFluidTank tank) {
		this.pos = pos;
		this.tank = tank;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		tank = new MultiFluidTank(0, null).readFromNBT(tag); // TODO
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());

		NBTTagCompound tag = new NBTTagCompound();
		if(tank != null) {
			tank.writeToNBT(tag);
		}
		ByteBufUtils.writeTag(buf, tag);
	}

}
