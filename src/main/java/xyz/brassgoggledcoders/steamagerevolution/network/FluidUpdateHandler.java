package xyz.brassgoggledcoders.steamagerevolution.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityBasicFluidTank;

public class FluidUpdateHandler implements IMessageHandler<PacketFluidUpdate, IMessage> {
	public FluidUpdateHandler() {

	}

	@Override
	public IMessage onMessage(PacketFluidUpdate message, MessageContext ctx) {
		Minecraft minecraft = Minecraft.getMinecraft();
		final WorldClient worldClient = minecraft.world;
		minecraft.addScheduledTask(new Runnable() {
			public void run() {
				processMessage(worldClient, message);
			}
		});
		return null;
	}

	private void processMessage(WorldClient worldClient, PacketFluidUpdate message) {
		TileEntity te = worldClient.getTileEntity(message.pos);
		if(te instanceof TileEntityBasicFluidTank) {
			TileEntityBasicFluidTank tile = (TileEntityBasicFluidTank) te;
			tile.updateFluid(message.fluid);
		}
		else if(te instanceof TileEntityCastingBench) {
			TileEntityCastingBench tile = (TileEntityCastingBench) te;
			tile.updateFluid(message.fluid);
		}
	}
}
