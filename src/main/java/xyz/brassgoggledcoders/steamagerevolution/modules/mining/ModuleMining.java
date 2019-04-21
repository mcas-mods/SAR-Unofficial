package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.awt.Color;
import java.util.List;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModuleDependency;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.EntityRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedMaterial;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedMaterialRegistry;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockCrushedLoader;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockHeavyOre;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill.BlockDrillFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill.BlockDrillOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartOreCarrier;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemMinecartOreCarrier;

@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class ModuleMining extends ModuleBase {

	static String[] metals = new String[] { "Iron", "Gold" };

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.mining.ClientProxy";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	public List<IDependency> getDependencies(List<IDependency> dependencies) {
		dependencies.add(new ModuleDependency("Metalworking"));
		return dependencies;
	}

	@Override
	public String getName() {
		return "Mining";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		super.registerBlocks(configRegistry, blockRegistry);
		for (String type : metals) {
			blockRegistry.register(new BlockHeavyOre(type));
		}

		blockRegistry.register(new BlockDrillFrame());
		blockRegistry.register(new BlockDrillOutput());

		blockRegistry.register(new BlockCrushedLoader());
	}

	@SubscribeEvent
	public static void registerCargo(CrushedMaterialRegistry.CrushedMaterialRegistryEvent registerEvent) {
		for (String type : metals) {
			registerEvent.getRegistry().addEntry(new CrushedMaterial(type.toLowerCase(), Color.GRAY.getRGB()));
		}
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		super.registerItems(configRegistry, itemRegistry);
		itemRegistry.register(new ItemMinecartOreCarrier());
	}

	@Override
	public void registerEntities(ConfigRegistry configRegistry, EntityRegistry entityRegistry) {
		super.registerEntities(configRegistry, entityRegistry);
		entityRegistry.register(EntityMinecartOreCarrier.class);
	}

}
