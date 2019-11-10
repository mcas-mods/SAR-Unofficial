package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import com.teamacronymcoders.base.multiblocksystem.MultiblockTileEntityBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.brassgoggledcoders.steamagerevolution.SARCaps;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryHeatable;

public class HandlerSetTemperature implements IMessageHandler<PacketSetTemperature, IMessage> {
    @Override
    public IMessage onMessage(PacketSetTemperature message, MessageContext ctx) {
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

    private void processMessage(WorldClient worldClient, PacketSetTemperature message) {
        TileEntity te = worldClient.getTileEntity(message.pos);
        IHasInventory<?> inventoryHolder;
        if(te instanceof IHasInventory) {
            inventoryHolder = (IHasInventory<?>) te;
        }
        else {
            MultiblockTileEntityBase<?> tile = (MultiblockTileEntityBase<?>) te;
            inventoryHolder = (IHasInventory<?>) tile.getMultiblockController();
        }
        
        if(inventoryHolder != null && inventoryHolder.getInventory().hasCapability(SARCaps.HEATABLE, null)) {
        	((InventoryHeatable)inventoryHolder.getInventory()).getCapability(SARCaps.HEATABLE, null).setCurrentTemperature(message.value);
        }
    }
}
