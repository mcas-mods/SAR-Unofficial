package xyz.brassgoggledcoders.steamagerevolution.network;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityBasicFluidTank;

	public class FluidUpdateHandler implements IMessageHandler<PacketFluidUpdate, IMessage> {
		public FluidUpdateHandler() {

		}

		@Override
		public IMessage onMessage(PacketFluidUpdate message, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.pos);
			if(te instanceof TileEntityBasicFluidTank) {
				TileEntityBasicFluidTank tankblock = (TileEntityBasicFluidTank) te;
				tankblock.updateFluid(message.fluid);
			}
			return null;
		}
	}
