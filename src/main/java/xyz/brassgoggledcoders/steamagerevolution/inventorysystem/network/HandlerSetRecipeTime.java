package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;

public class HandlerSetRecipeTime implements IMessageHandler<PacketSetRecipeTime, IMessage> {
	public HandlerSetRecipeTime() {

	}

	@Override
	public IMessage onMessage(PacketSetRecipeTime message, MessageContext ctx) {
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

	private void processMessage(WorldClient worldClient, PacketSetRecipeTime message) {
		TileEntity te = worldClient.getTileEntity(message.pos);
		if(te instanceof IHasInventory) {
			InventoryBasic inventory = ((IHasInventory<?>) te).getInventory();
			if(inventory instanceof InventoryRecipe) {
				InventoryRecipe rInventory = (InventoryRecipe) inventory;
				rInventory.clientTicksToComplete = message.maxTicks;
			}
		}
	}
}
