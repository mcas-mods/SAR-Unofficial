package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking;

import java.util.List;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigEntry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.DistillerRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.VatRecipe.VatRecipeBuilder;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.items.ItemDie;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.CrucibleRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.SteamHammerRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.SteamFurnaceRecipe;

@Module(value = SteamAgeRevolution.MODID)
@ObjectHolder(SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class ModuleMetalworking extends ModuleBase {

	public static final Item charcoal_powder = null;
	public static final Item die = null;
	public static final Item hammer = null;
	public static final Block steamhammer_frame = null;

	// Same as TiCon. Cannot be final because ObjectHolder tries to map to them o.O
	public static int VALUE_INGOT = 144;
	public static int VALUE_NUGGET = VALUE_INGOT / 9;
	public static int VALUE_BLOCK = VALUE_INGOT * 9;
	// public static final int VALUE_ORE = VALUE_INGOT * 2;

	public static List<String> knownMetalTypes = Lists.newArrayList();

	public static DamageSource damageSourceHammer =
			new DamageSource("hammer").setDifficultyScaled().setDamageBypassesArmor().setDamageIsAbsolute();

	public static int plateCount, dustCount;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		this.getConfigRegistry().addEntry("plateCount", new ConfigEntry("balance", "plateCount", Type.INTEGER, "1"));
		plateCount = this.getConfigRegistry().getInt("plateCount", 1);
		this.getConfigRegistry().addEntry("dustCount", new ConfigEntry("balance", "dustCount", Type.INTEGER, "1"));
		dustCount = this.getConfigRegistry().getInt("dustCount", 1);
		this.getConfigRegistry().addCategoryComment("balance", "Adjust number of items produced in recipes", "General");
		knownMetalTypes.add("Iron");
		knownMetalTypes.add("Gold");
		super.preInit(event);
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ClientProxy";
	}

	@Override
	public String getName() {
		return "Metalworking";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		// new BlockAlloyFurnaceController(Material.ANVIL, "alloy_furnace_controller");
		// blockRegistry.register(alloyFurnaceController);
		blockRegistry.register(new BlockAlloyFurnaceFrame(Material.ROCK, "alloy_furnace_frame"));
		blockRegistry.register(new BlockAlloyFurnaceFluidInput(Material.ROCK, "alloy_furnace_fluid_input"));
		blockRegistry.register(new BlockAlloyFurnaceFluidOutput(Material.ROCK, "alloy_furnace_fluid_output"));

		blockRegistry.register(new BlockSteelworksFrame(Material.ROCK, "steelworks_frame"));
		blockRegistry.register(new BlockSteelworksIronInput(Material.ROCK, "steelworks_iron_input"));
		blockRegistry.register(new BlockSteelworksCarbonInput(Material.ROCK, "steelworks_carbon_input"));
		blockRegistry.register(new BlockSteelworksSteamInput(Material.ROCK, "steelworks_steam_input"));
		blockRegistry.register(new BlockSteelworksSteelOutput(Material.ROCK, "steelworks_steel_output"));

		blockRegistry.register(new BlockSteamHammerAnvil(Material.ANVIL, "steamhammer_anvil"));
		blockRegistry.register(new BlockSteamHammerFrame(Material.IRON, "steamhammer_frame"));
		blockRegistry.register(new BlockSteamHammerHammer(Material.IRON, "steamhammer_hammer"));
		blockRegistry.register(new BlockSteamHammerShielding(Material.IRON, "steamhammer_shielding"));

		blockRegistry.register(new BlockCastingBench(Material.ANVIL, "casting_bench"));

		blockRegistry.register(new BlockCrucibleCasing(Material.IRON, "crucible_casing"));
		blockRegistry.register(new BlockCrucibleItemInput(Material.IRON, "crucible_item_input"));
		blockRegistry.register(new BlockCrucibleSteamInput(Material.IRON, "crucible_steam_input"));
		blockRegistry.register(new BlockCrucibleFluidOutput(Material.IRON, "crucible_fluid_output"));
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		itemRegistry.register(new ItemBase("hammer").setMaxStackSize(1).setMaxDamage(ToolMaterial.IRON.getMaxUses()));
		itemRegistry.register(new ItemDie());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(
				new RecipesOreToDust().setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "ore_to_dust")));
		event.getRegistry().register(new RecipesIngotToPlate()
				.setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "ingot_to_plate")));

		for(ItemStack cooked : OreDictionary.getOres("listAllmeatcooked")) {
			SteamFurnaceRecipe.addSteamFurnaceRecipe(cooked, new ItemStack(charcoal_powder));
		}

		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.STONE), new ItemStack(Blocks.COBBLESTONE));
		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL));
		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND));

		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.DIRT), new ItemStack(Items.DIAMOND), "test");

		AlloyFurnaceRecipe.addAlloyFurnaceRecipe(FluidRegistry.getFluidStack("copper", VALUE_INGOT),
				FluidRegistry.getFluidStack("zinc", VALUE_INGOT), FluidRegistry.getFluidStack("brass", VALUE_INGOT));

		for(String metal : knownMetalTypes) {

			// Known to be non-null because it is how metal types are known
			ItemStack ingot = OreDictUtils.getPreferredItemStack("ingot" + metal);

			ItemStack ore = OreDictUtils.getPreferredItemStack("ore" + metal);
			ItemStack gear = OreDictUtils.getPreferredItemStack("gear" + metal);
			ItemStack plate = OreDictUtils.getPreferredItemStack("plate" + metal);
			ItemStack crushedOre = OreDictUtils.getPreferredItemStack("crushedOre" + metal);
			ItemStack nugget = OreDictUtils.getPreferredItemStack("nugget" + metal);
			ItemStack dust = OreDictUtils.getPreferredItemStack("dust" + metal);
			ItemStack crystal = OreDictUtils.getPreferredItemStack("crystal" + metal);
			FluidStack molten = FluidRegistry.getFluidStack(metal.toLowerCase(), VALUE_INGOT);
			FluidStack solution = FluidRegistry.getFluidStack(metal.toLowerCase() + "_solution", VALUE_NUGGET * 4);

			if(molten != null) {
				CrucibleRecipe.addRecipe(ingot, molten);
				CastingBlockRecipe.addRecipe(molten, ingot);
			}
			if(!plate.isEmpty()) {
				ItemStack plateCopy = plate.copy();
				plateCopy.setCount(plateCount);
				SteamHammerRecipe.addSteamHammerRecipe(ingot, plateCopy);
			}
			if(!gear.isEmpty()) {
				SteamHammerRecipe.addSteamHammerRecipe(ingot, gear, "gear");
			}
			if(!ore.isEmpty()) {
				GameRegistry.addSmelting(ore, ingot, 0.5F);
			}
			if(!dust.isEmpty()) {
				GameRegistry.addSmelting(dust, ingot, 0.5F);
			}
			if(!crushedOre.isEmpty()) {
				ItemStack nuggetCopy = nugget.copy();
				nuggetCopy.setCount(3);
				GameRegistry.addSmelting(crushedOre, nuggetCopy, 0.1f);
				ItemStack crushedOreCopy = crushedOre.copy();
				crushedOreCopy.setCount(4);
				SteamHammerRecipe.addSteamHammerRecipe(ore, crushedOreCopy);
			}
			if(!crystal.isEmpty()) {
				if(!nugget.isEmpty()) {
					GameRegistry.addSmelting(crystal, nugget, 0.3f);
				}
				if(solution != null) {
					new VatRecipeBuilder().setOutput(solution)
							.setFluids(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME / 4))
							.setItems(crushedOre).build();
					DistillerRecipe.addRecipe(solution,
							FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME / 6), crystal, 20);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onOreRegistered(OreDictionary.OreRegisterEvent event) {
		String name = event.getName();
		String[] splitName = name.split("(?=[A-Z])");
		if(splitName.length == 2) {
			if(splitName[0].equals("ingot")) {
				String metalType = splitName[1];
				if(!knownMetalTypes.contains(metalType)) {
					knownMetalTypes.add(metalType);
					SteamAgeRevolution.instance.getLogger().devInfo("Metal type detected: " + metalType);
				}
			}
		}
	}

}
