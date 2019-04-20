package xyz.brassgoggledcoders.steamagerevolution.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.tileenties.TileEntityGunsmithingBench;

public class HandlerGunCraft implements IMessageHandler<PacketGunCraft, IMessage> {
	public HandlerGunCraft() {

	}

	@Override
	public IMessage onMessage(PacketGunCraft message, MessageContext ctx) {
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		WorldServer worldServer = serverPlayer.getServerWorld();
		worldServer.addScheduledTask(() -> {
			BlockPos pos = message.pos;
			if(worldServer.isBlockLoaded(pos, false)) {
				TileEntity te = worldServer.getTileEntity(message.pos);
				((TileEntityGunsmithingBench) te).attemptCraft();
			}
		});
		return null;
	}
}
