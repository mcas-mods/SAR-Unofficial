package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities.TileEntityBoilerPart;

public class HandlerSetBoilerValue implements IMessageHandler<PacketSetBoilerValue, IMessage> {
    @Override
    public IMessage onMessage(PacketSetBoilerValue message, MessageContext ctx) {
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

    private void processMessage(WorldClient worldClient, PacketSetBoilerValue message) {
        TileEntity te = worldClient.getTileEntity(message.pos);
        if(te instanceof TileEntityBoilerPart) {
            if(message.isTemp) {
                ((TileEntityBoilerPart) te).getMultiblockController().currentTemperature = message.value;
            }
            else {
                ((TileEntityBoilerPart) te).getMultiblockController().currentBurnTime = message.value;
            }
        }
    }
}
