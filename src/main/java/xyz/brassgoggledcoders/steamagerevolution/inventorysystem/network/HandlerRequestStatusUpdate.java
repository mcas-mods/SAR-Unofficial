package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;

public class HandlerRequestStatusUpdate implements IMessageHandler<PacketRequestStatusUpdate, PacketStatusUpdate> {

	TileEntity te;

	@Override
	public PacketStatusUpdate onMessage(PacketRequestStatusUpdate message, MessageContext ctx) {
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		WorldServer worldServer = serverPlayer.getServerWorld();
		worldServer.addScheduledTask(() -> {
			this.te = worldServer.getTileEntity(message.machineLocation);
		});
		if(te != null && te instanceof IHasInventory<?>) {
			InventoryBasic inv = ((IHasInventory<?>) te).getInventory();
			if(inv instanceof InventoryRecipe) {
				InventoryRecipe invR = (InventoryRecipe) inv;
				return new PacketStatusUpdate(te.getPos(), invR.getCurrentTicks(),
						invR.getRecipeError().getNetworkID());
			}
		}
		return null;
	}

}
