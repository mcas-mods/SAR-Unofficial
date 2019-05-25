package xyz.brassgoggledcoders.steamagerevolution.network;

import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;

public class HandlerFluidUpdate implements IMessageHandler<PacketFluidUpdate, IMessage> {
	public HandlerFluidUpdate() {

	}

	@Override
	public IMessage onMessage(PacketFluidUpdate message, MessageContext ctx) {
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

	private void processMessage(WorldClient worldClient, PacketFluidUpdate message) {
		TileEntity te = worldClient.getTileEntity(message.pos);
		if (te instanceof IMachineHasInventory) {
			IMachineHasInventory tile = (IMachineHasInventory) te;
			tile.getInventory().updateFluid(message);
		} else {
			MultiblockTileEntityBase<?> tile = (MultiblockTileEntityBase<?>) te;
			IMachineHasInventory controller = (IMachineHasInventory) tile.getMultiblockController();
			controller.getInventory().updateFluid(message);
		}
	}
}
