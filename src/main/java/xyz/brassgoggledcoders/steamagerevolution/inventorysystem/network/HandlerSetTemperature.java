package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.SARCaps;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;

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
        if(te instanceof IHasInventory<?>
                && ((IHasInventory<?>) te).getInventory().hasCapability(SARCaps.HEATABLE, null)) {
            ((IHasInventory<?>) te).getInventory().getCapability(SARCaps.HEATABLE, null)
                    .setCurrentTemperature(message.temp);
        }
    }
}
