package xyz.brassgoggledcoders.steamagerevolution.modules.processing;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IModuleProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySawmillAxle.class, new
        // TileEntitySawmillAxleRenderer());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }
}
