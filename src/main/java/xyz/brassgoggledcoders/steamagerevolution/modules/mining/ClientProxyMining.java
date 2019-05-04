package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;
import com.teamacronymcoders.base.renderer.entity.minecart.RenderMinecartBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartDrilling;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartCarrier;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.RenderMinecartDrilling;

public class ClientProxyMining implements IModuleProxy {
	
	public ResourceListener listener;
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityMinecartCarrier.class, RenderMinecartBase::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMinecartDrilling.class, RenderMinecartDrilling::new);
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		listener = new ResourceListener();
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(listener);
	}
}
