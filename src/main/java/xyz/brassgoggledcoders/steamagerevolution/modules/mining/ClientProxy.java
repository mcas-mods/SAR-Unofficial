package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy  implements IModuleProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityMinecartOreCarrier.class, RenderMinecartOreCarrier::new);
	}

	@Override
	public void init(FMLInitializationEvent event) {	
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
}
