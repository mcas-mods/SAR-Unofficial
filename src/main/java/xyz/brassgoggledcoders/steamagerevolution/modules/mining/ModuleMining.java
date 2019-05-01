package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.util.List;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModuleDependency;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.EntityRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import stanhebben.zenscript.util.StringUtil;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolid;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.SemisolidRecipe.Builder;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockHeavyOreGenerator;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockRailDumping;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockSemisolidLoader;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartDrilling;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder.BlockGrinderFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder.BlockGrinderInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemMinecartDrilling;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemMinecartSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.worldgen.OreGenerator;

@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
@ObjectHolder(SteamAgeRevolution.MODID)
public class ModuleMining extends ModuleBase {

	public static DamageSource damageSourceGrinder = new DamageSource("grinder").setDifficultyScaled()
			.setDamageBypassesArmor().setDamageIsAbsolute();
	
	public static final ItemMinecart minecart_semisolid = null;
	public static final ItemMinecart minecart_drilling = null;

	
	//TODO: Auto build this list from registered heavy ores?
	static String[] materials = {"iron", "gold", "copper", "zinc", "tin", "aluminum", "lead"};

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

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		for (ISemisolid ssolid : SteamAgeRevolution.semisolidRegistry.getEntries()) {
			ItemStack crushedOreStack = OreDictUtils
					.getPreferredItemStack("crushedOre" + StringUtil.capitalize(ssolid.getRegistryName().getPath()));
			if (!crushedOreStack.isEmpty()) {
				((Builder) new SemisolidRecipe.Builder("grinder").setItemOutputs(crushedOreStack))
						.setSemisolidInputs(new SemisolidStack(ssolid, 1)).setCraftTime(10).build();
			}
		}
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		super.registerBlocks(configRegistry, blockRegistry);

		// blockRegistry.register(new BlockDrillFrame());
		// blockRegistry.register(new BlockDrillOutput());
		
		for(String type : materials) {
			blockRegistry.register(new BlockHeavyOreGenerator(type));
			OreGenerator.heavyOresToGenerate.add(type);
		}

		blockRegistry.register(new BlockRailDumping());
		blockRegistry.register(new BlockSemisolidLoader());

		blockRegistry.register(new BlockGrinderFrame());
		blockRegistry.register(new BlockGrinderInput());
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
