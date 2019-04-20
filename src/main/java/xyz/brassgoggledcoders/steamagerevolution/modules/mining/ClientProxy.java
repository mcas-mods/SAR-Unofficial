package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;
import com.teamacronymcoders.base.renderer.entity.minecart.RenderMinecartBase;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartOreCarrier;

public class ClientProxy  implements IModuleProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityMinecartOreCarrier.class, RenderMinecartBase::new);
	}

	@Override
	public void init(FMLInitializationEvent event) {	
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
}
