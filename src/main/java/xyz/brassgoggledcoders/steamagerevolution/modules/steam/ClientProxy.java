package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class ClientProxy implements IModuleProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@Override
	public void init(FMLInitializationEvent event) {
		com.teamacronymcoders.base.client.ClientProxy.registerFluidModel(ModuleSteam.steamBlock,
				new ModelResourceLocation(SteamAgeRevolution.MODID + ":fluids", "steam"));
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}
}
