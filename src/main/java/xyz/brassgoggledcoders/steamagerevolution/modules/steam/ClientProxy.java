package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import com.teamacronymcoders.base.Base;
import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.renderers.TileEntityBoilerSteamGaugeRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.renderers.TileEntityBoilerWaterGaugeRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerSteamGauge;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerWaterGauge;

@ObjectHolder(SteamAgeRevolution.MODID)
public class ClientProxy implements IModuleProxy {

	public static final Block steam = null;

	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@Override
	public void init(FMLInitializationEvent event) {
		Base.instance.getLibProxy().registerFluidModel(steam, new ResourceLocation(SteamAgeRevolution.MODID, "steam"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoilerWaterGauge.class,
				new TileEntityBoilerWaterGaugeRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoilerSteamGauge.class,
				new TileEntityBoilerSteamGaugeRenderer());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}
}
