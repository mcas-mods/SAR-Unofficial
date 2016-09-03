package xyz.brassgoggledcoders.steamagerevolution.proxies;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ModuleSteam;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerModels() {
		if(SteamAgeRevolution.instance.getModuleHandler().isModuleEnabled("Steam")) {
			xyz.brassgoggledcoders.boilerplate.proxies.ClientProxy.registerFluidModel(ModuleSteam.steam_block,
					new ModelResourceLocation(SteamAgeRevolution.MODID + ":fluids", "steam"));
		}
	}
}
