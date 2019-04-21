package xyz.brassgoggledcoders.steamagerevolution.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartSemisolid;

public class HandlerSemisolidCart implements IMessageHandler<PacketSemisolidCart, IMessage> {
	public HandlerSemisolidCart() {
	}

	@Override
	public IMessage onMessage(PacketSemisolidCart message, MessageContext ctx) {
		Minecraft minecraft = Minecraft.getMinecraft();
		final WorldClient worldClient = minecraft.world;
		minecraft.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				processMessage(worldClient, message);
			}
		});
		return null;
	}

	private void processMessage(WorldClient worldClient, PacketSemisolidCart message) {
		Entity entity = worldClient.getEntityByID(message.id);
		if(entity instanceof EntityMinecartSemisolid) {
			((EntityMinecartSemisolid)entity).getInventory().ore.getHandler().deserializeNBT(message.tag);
		}
	}
}
