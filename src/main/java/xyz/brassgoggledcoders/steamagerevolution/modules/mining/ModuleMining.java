package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.awt.Color;
import java.util.List;

import com.teamacronymcoders.base.materialsystem.MaterialSystem;
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
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.Semisolid;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidRegistry;
import xyz.brassgoggledcoders.steamagerevolution.modules.materials.ModuleMaterials;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockHeavyOre;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockRailDumping;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockSemisolidLoader;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill.BlockDrillFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill.BlockDrillOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartDrilling;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemMinecartDrilling;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemMinecartSemisolid;

@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class ModuleMining extends ModuleBase {

	String[] oreTypes = new String[] {"Iron", "Gold", "Coal"};
	
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
		
		for (String type : oreTypes) {
			//TODO Integrate into the material system
			blockRegistry.register(new BlockHeavyOre(type));
		}

		blockRegistry.register(new BlockDrillFrame());
		blockRegistry.register(new BlockDrillOutput());

		blockRegistry.register(new BlockRailDumping());
		blockRegistry.register(new BlockSemisolidLoader());
	}

	@SubscribeEvent
	public static void registerSemisolids(SemisolidRegistry.SemisolidRegistryEvent registerEvent) {
		for (String type : ModuleMaterials.knownMetalTypes) {
			int color = Color.GRAY.getRGB();
			//TODO Integrated the Semisolid registry into the MaterialSystem as a custom part type
			if(MaterialSystem.getMaterial(type) != null) {
				color = MaterialSystem.getMaterial(type).getColor().getRGB();
			}
			registerEvent.getRegistry().addEntry(new Semisolid(type.toLowerCase(), color));
		}
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		super.registerItems(configRegistry, itemRegistry);
		itemRegistry.register(new ItemMinecartSemisolid());
		itemRegistry.register(new ItemMinecartDrilling());
	}

	@Override
	public void registerEntities(ConfigRegistry configRegistry, EntityRegistry entityRegistry) {
		super.registerEntities(configRegistry, entityRegistry);
		entityRegistry.register(EntityMinecartSemisolid.class);
		entityRegistry.register(EntityMinecartDrilling.class);
	}

}
