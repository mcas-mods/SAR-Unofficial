package xyz.brassgoggledcoders.steamagerevolution.network;

import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeRegistry;

public class HandlerRecipeUpdate implements IMessageHandler<PacketRecipeUpdate, IMessage> {
	public HandlerRecipeUpdate() {

	}

	@Override
	public IMessage onMessage(PacketRecipeUpdate message, MessageContext ctx) {
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

	private void processMessage(WorldClient worldClient, PacketRecipeUpdate message) {
		TileEntity te = worldClient.getTileEntity(message.pos);
		MultiblockTileEntityBase<?> tile = (MultiblockTileEntityBase<?>) te;
		IHasInventory controller = (IHasInventory) tile.getMultiblockController();
		/* TODO */
		controller.setCurrentRecipe(RecipeRegistry.getRecipeByID(message.type, message.id));

	}
}