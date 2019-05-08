package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModuleDependency;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.EntityRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import stanhebben.zenscript.util.StringUtil;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartCarrier;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartDrilling;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder.BlockGrinderFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder.BlockGrinderInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder.BlockGrinderOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemMinecartCarrier;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemMinecartDrilling;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe.MachineRecipeBuilder;

@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
@ObjectHolder(SteamAgeRevolution.MODID)
public class ModuleMining extends ModuleBase {

	public static DamageSource damageSourceGrinder = new DamageSource("grinder").setDifficultyScaled()
			.setDamageBypassesArmor().setDamageIsAbsolute();
	private static String name = "[" + SteamAgeRevolution.MODNAME + "]";
	public static GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(name.getBytes()), name);
	
	public static final ItemMinecart minecart_carrier = null;
	public static final ItemMinecart minecart_drilling = null;

	static String[] materials = {"iron", "gold", "copper", "zinc"};//, "tin", "aluminum", "lead"};

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.mining.ClientProxyMining";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		for(String material : materials) {
			LootTableList.register(new ResourceLocation(SteamAgeRevolution.MODID, "heavy_ore_" + material));
		}
		LootFunctionManager.registerFunction(new LootFunctionOredict.Serializer());
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
		for (String material : materials) {
			ItemStack rockStack = OreDictUtils
					.getPreferredItemStack("rock" + StringUtil.capitalize(material));
			ItemStack crushedOreStack = OreDictUtils
					.getPreferredItemStack("crushedOre" + StringUtil.capitalize(material));
			if (!rockStack.isEmpty() && !crushedOreStack.isEmpty()) {
				ItemStack co = crushedOreStack.copy();
				co.setCount(2);
				new MachineRecipeBuilder("grinder").setItemInputs(rockStack).setItemOutputs(co).setCraftTime(20).setSteamCost(Fluid.BUCKET_VOLUME / 2).build();
			}
			
			new MachineRecipeBuilder("grinder").setItemInputs(new ItemStack(Blocks.STONE))
			.setItemOutputs(new ItemStack(Blocks.COBBLESTONE)).setSteamCost(Fluid.BUCKET_VOLUME / 10).setCraftTime(10).build();
			new MachineRecipeBuilder("grinder").setItemInputs(new ItemStack(Blocks.COBBLESTONE))
				.setItemOutputs(new ItemStack(Blocks.GRAVEL)).setSteamCost(Fluid.BUCKET_VOLUME / 10).setCraftTime(10).build();
			new MachineRecipeBuilder("grinder").setSteamCost(Fluid.BUCKET_VOLUME / 10).setCraftTime(10).setItemInputs(new ItemStack(Blocks.GRAVEL))
				.setItemOutputs(new ItemStack(Blocks.SAND)).setSteamCost(Fluid.BUCKET_VOLUME / 10).setCraftTime(10).build();
		}
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		super.registerBlocks(configRegistry, blockRegistry);
		blockRegistry.register(new BlockGrinderFrame());
		blockRegistry.register(new BlockGrinderInput());
		blockRegistry.register(new BlockGrinderOutput());
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		super.registerItems(configRegistry, itemRegistry);
		itemRegistry.register(new ItemMinecartCarrier());
		itemRegistry.register(new ItemMinecartDrilling());
	}

	@Override
	public void registerEntities(ConfigRegistry configRegistry, EntityRegistry entityRegistry) {
		super.registerEntities(configRegistry, entityRegistry);
		entityRegistry.register(EntityMinecartCarrier.class);
		entityRegistry.register(EntityMinecartDrilling.class);
	}

}
