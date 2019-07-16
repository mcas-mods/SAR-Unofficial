package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.RecipeError;

public class HandlerStatusUpdate implements IMessageHandler<PacketStatusUpdate, IMessage> {

	@Override
	public IMessage onMessage(PacketStatusUpdate message, MessageContext ctx) {
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

	private void processMessage(WorldClient worldClient, PacketStatusUpdate message) {
		TileEntity te = worldClient.getTileEntity(message.machineLocation);
		if(te instanceof IHasInventory) {
			InventoryBasic inventory = ((IHasInventory<?>) te).getInventory();
			if(inventory instanceof InventoryRecipe) {
				InventoryRecipe rInventory = (InventoryRecipe) inventory;
				rInventory.setCurrentTicks(message.currentProgress);
				rInventory.setRecipeError(RecipeError.fromNetworkID(message.errorID));
			}
		}
	}

}
