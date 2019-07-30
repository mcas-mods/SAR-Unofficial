package xyz.brassgoggledcoders.steamagerevolution;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.steamagerevolution.api.IFumeProducer;
import xyz.brassgoggledcoders.steamagerevolution.heat.*;

@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class SARCapabilities {
    @CapabilityInject(IFumeProducer.class)
    public static Capability<IFumeProducer> FUME_PRODUCER;
    @CapabilityInject(IHeatMap.class)
    public static Capability<IHeatMap> HEAT_MAP;

    public static void register() {
        CapabilityManager.INSTANCE.register(IFumeProducer.class, new NOPStorage<IFumeProducer>(),
                new NullFactory<IFumeProducer>());
        CapabilityManager.INSTANCE.register(IHeatMap.class, new HeatMapStorage(), new NullFactory<>());
    }

    @SubscribeEvent
    public static void attachTileCapabilities(AttachCapabilitiesEvent<TileEntity> event) {
        if(event.getObject() instanceof TileEntityFurnace) {
            event.addCapability(new ResourceLocation(SteamAgeRevolution.MODID, "fume_collector"),
                    new FurnaceCapabilityProvider((TileEntityFurnace) event.getObject()));
        }
    }
}
