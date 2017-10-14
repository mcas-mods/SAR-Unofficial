package xyz.brassgoggledcoders.steamagerevolution.network;

import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartStackCallback;

public class HandlerItemUpdate implements IMessageHandler<PacketItemUpdate, IMessage> {
	public HandlerItemUpdate() {

	}

	@Override
	public IMessage onMessage(PacketItemUpdate message, MessageContext ctx) {
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

	private void processMessage(WorldClient worldClient, PacketItemUpdate message) {
		TileEntity te = worldClient.getTileEntity(message.pos);
		// FIXME Temporary hardcode
		MultiblockTileEntityBase<?> tile = (MultiblockTileEntityBase<?>) te;
		ISmartStackCallback controller = (ISmartStackCallback) tile.getMultiblockController();
		controller.updateStack(message);
	}
}
