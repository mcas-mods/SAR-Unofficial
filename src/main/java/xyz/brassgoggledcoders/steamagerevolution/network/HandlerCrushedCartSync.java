package xyz.brassgoggledcoders.steamagerevolution.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartOreCarrier;

public class HandlerCrushedCartSync implements IMessageHandler<PacketCrushedCartSync, IMessage> {
	public HandlerCrushedCartSync() {

	}

	@Override
	public IMessage onMessage(PacketCrushedCartSync message, MessageContext ctx) {
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

	private void processMessage(WorldClient worldClient, PacketCrushedCartSync message) {
		Entity entity = worldClient.getEntityByID(message.id);
		if(entity instanceof EntityMinecartOreCarrier) {
			((EntityMinecartOreCarrier)entity).getInventory().ore.getHandler().deserializeNBT(message.tag);
		}
	}
}
