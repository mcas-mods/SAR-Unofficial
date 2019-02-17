package xyz.brassgoggledcoders.steamagerevolution.network;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketMultiFluidUpdate implements IMessage {

	public BlockPos pos;
	public ArrayList<FluidStack> fluids;
	public int id;

	public PacketMultiFluidUpdate() {
	}

	public PacketMultiFluidUpdate(BlockPos pos, ArrayList<FluidStack> fluids, int id) {
		this.pos = pos;
		this.id = id;
		this.fluids = fluids;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		int size = buf.readInt();
		fluids = Lists.newArrayListWithCapacity(size);
		for(int i = 0; i < size; i++) {
			NBTTagCompound tag = ByteBufUtils.readTag(buf);
			if(tag != null) {
				FluidStack stack = FluidStack.loadFluidStackFromNBT(tag);
				if(stack != null) {
					fluids.add(stack);
				}
			}
		}
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(fluids.size());
		for(FluidStack stack : fluids) {
			ByteBufUtils.writeTag(buf, stack.writeToNBT(new NBTTagCompound()));
		}
		buf.writeInt(id);
	}

}
