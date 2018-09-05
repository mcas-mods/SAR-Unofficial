package xyz.brassgoggledcoders.steamagerevolution.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketFluidUpdate implements IMessage {

    public BlockPos pos;
    public FluidStack fluid;
    public int id;

    public PacketFluidUpdate() {
    }

    public PacketFluidUpdate(BlockPos pos, FluidStack fluid, int id) {
        this.pos = pos;
        this.fluid = fluid;
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        NBTTagCompound tag = ByteBufUtils.readTag(buf);
        fluid = FluidStack.loadFluidStackFromNBT(tag);
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        NBTTagCompound tag = new NBTTagCompound();
        if (fluid != null) {
            fluid.writeToNBT(tag);
        }
        ByteBufUtils.writeTag(buf, tag);
        buf.writeInt(id);
    }

}
