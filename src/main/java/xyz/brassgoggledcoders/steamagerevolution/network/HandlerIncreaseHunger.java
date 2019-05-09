package xyz.brassgoggledcoders.steamagerevolution.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerIncreaseHunger implements IMessageHandler<PacketIncreaseHunger, IMessage> {
	public HandlerIncreaseHunger() {

	}

	@Override
	public IMessage onMessage(PacketIncreaseHunger message, MessageContext ctx) {
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		WorldServer worldServer = serverPlayer.getServerWorld();
		worldServer.addScheduledTask(() -> {
			serverPlayer.getFoodStats().addExhaustion(message.hunger);

		});
		return null;
	}
}
