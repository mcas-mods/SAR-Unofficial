package xyz.brassgoggledcoders.steamagerevolution.proxies;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ModuleSteam;
import xyz.brassgoggledcoders.steamagerevolution.modules.vanity.ModuleVanity;
import xyz.brassgoggledcoders.steamagerevolution.modules.vanity.renderers.TileEntityTrunkRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.vanity.tileentities.TileEntityTrunk;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerModels() {
		if(SteamAgeRevolution.instance.getModuleHandler().isModuleEnabled("Steam")) {
			xyz.brassgoggledcoders.boilerplate.proxies.ClientProxy.registerFluidModel(ModuleSteam.steam_block,
					new ModelResourceLocation(SteamAgeRevolution.MODID + ":fluids", "steam"));
		}
		if(SteamAgeRevolution.instance.getModuleHandler().isModuleEnabled("Vanity")) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrunk.class, new TileEntityTrunkRenderer());
			ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(ModuleVanity.trunk), 0, TileEntityTrunk.class);
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModuleVanity.trunk), 0,
					new ModelResourceLocation("steamagerevolution:trunk", "normal"));
		}
	}
}
