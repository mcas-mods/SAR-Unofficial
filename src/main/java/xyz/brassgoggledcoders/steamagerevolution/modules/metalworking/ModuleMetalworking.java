package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking;

import java.util.List;

import com.google.common.collect.Lists;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.materials.ModuleMaterials;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.blocks.BlockCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.items.ItemDie;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.items.ItemHammer;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceFluidInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceFluidOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.blocks.BlockCrucibleCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.blocks.BlockCrucibleFluidOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.blocks.BlockCrucibleItemInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.blocks.BlockCrucibleSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.blocks.BlockSteamHammerAnvil;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.blocks.BlockSteamHammerFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.blocks.BlockSteamHammerHammer;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.blocks.BlockSteamHammerShielding;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.blocks.BlockSteelworksCarbonInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.blocks.BlockSteelworksFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.blocks.BlockSteelworksIronInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.blocks.BlockSteelworksSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.blocks.BlockSteelworksSteelOutput;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe.MachineRecipeBuilder;

@Module(value = SteamAgeRevolution.MODID)
@ObjectHolder(SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class ModuleMetalworking extends ModuleBase {

	public static final Item die = null;
	public static final Item hammer = null;
	public static final Block steamhammer_frame = null;

	public static List<String> knownMetalTypes = Lists.newArrayList();

	public static DamageSource damageSourceHammer = new DamageSource("hammer").setDifficultyScaled()
			.setDamageBypassesArmor().setDamageIsAbsolute();

	public static int plateCount, dustCount;

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(
				new RecipesOreToDust().setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "ore_to_dust")));
		event.getRegistry().register(new RecipesIngotToPlate()
				.setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "ingot_to_plate")));

		new MachineRecipeBuilder("steam hammer").setItemInputs(new ItemStack(Blocks.STONE))
				.setItemOutputs(new ItemStack(Blocks.COBBLESTONE)).build();
		new MachineRecipeBuilder("steam hammer").setItemInputs(new ItemStack(Blocks.COBBLESTONE))
				.setItemOutputs(new ItemStack(Blocks.GRAVEL)).build();
		new MachineRecipeBuilder("steam hammer").setItemInputs(new ItemStack(Blocks.GRAVEL))
				.setItemOutputs(new ItemStack(Blocks.SAND)).build();

		new MachineRecipeBuilder("alloy forge")
				.setFluidInputs(FluidRegistry.getFluidStack("copper", RecipeUtil.VALUE_INGOT),
						FluidRegistry.getFluidStack("zinc", RecipeUtil.VALUE_INGOT))
				.setFluidOutputs(FluidRegistry.getFluidStack("brass", RecipeUtil.VALUE_INGOT * 2)).build();

		new MachineRecipeBuilder("steelworks")
				.setFluidInputs(FluidRegistry.getFluidStack("iron", RecipeUtil.VALUE_NUGGET))
				.setItemInputs(new ItemStack(Items.COAL, 1, 1))
				.setFluidOutputs(FluidRegistry.getFluidStack("steel", RecipeUtil.VALUE_NUGGET))
				.setSteamCost(Fluid.BUCKET_VOLUME / 10).setCraftTime(600).build();

		new MachineRecipeBuilder("steelworks")
				.setFluidInputs(FluidRegistry.getFluidStack("iron", RecipeUtil.VALUE_BLOCK))
				.setItemInputs(new ItemStack(ModuleMaterials.charcoal_block))
				.setFluidOutputs(FluidRegistry.getFluidStack("steel", RecipeUtil.VALUE_BLOCK))
				.setSteamCost(Fluid.BUCKET_VOLUME * 10).setCraftTime(6000).build();

		for(String metal : knownMetalTypes) {

			// Known to be non-null because it is how metal types are known
			String ingot = "ingot" + metal;
			ItemStack ingotStack = OreDictUtils.getPreferredItemStack("ingot" + metal);

			String ore = "ore" + metal;
			String gear = "gear" + metal;
			String plate = "plate" + metal;
			String crushedOre = "crushedOre" + metal;
			String nugget = "nugget" + metal;
			String dust = "dust" + metal;
			String crystal = "crystal" + metal;
			String block = "block" + metal;

			ItemStack oreStack = OreDictUtils.getPreferredItemStack(ore);
			ItemStack gearStack = OreDictUtils.getPreferredItemStack(gear);
			ItemStack plateStack = OreDictUtils.getPreferredItemStack(plate);
			ItemStack crushedOreStack = OreDictUtils.getPreferredItemStack(crushedOre);
			ItemStack nuggetStack = OreDictUtils.getPreferredItemStack(nugget);
			ItemStack dustStack = OreDictUtils.getPreferredItemStack(dust);
			ItemStack crystalStack = OreDictUtils.getPreferredItemStack(crystal);
			FluidStack molten = FluidRegistry.getFluidStack(metal.toLowerCase(), RecipeUtil.VALUE_INGOT);
			FluidStack solution = FluidRegistry.getFluidStack(metal.toLowerCase() + "_solution",
					RecipeUtil.VALUE_NUGGET * 4);

			if(molten != null) {
				FluidStack moltenCopy = molten.copy();
				moltenCopy.amount = RecipeUtil.VALUE_NUGGET;
				new MachineRecipeBuilder("crucible").setItemInputs(nugget).setFluidOutputs(moltenCopy)
						.setSteamCost(Fluid.BUCKET_VOLUME / 32).setCraftTime(14).build();
				FluidStack moltenCopy2 = molten.copy();
				moltenCopy2.amount = RecipeUtil.VALUE_BLOCK;
				new MachineRecipeBuilder("crucible").setItemInputs(block).setFluidOutputs(moltenCopy2)
						.setSteamCost(Fluid.BUCKET_VOLUME).setCraftTime(1200).build();
				new MachineRecipeBuilder("crucible").setItemInputs(ingot).setFluidOutputs(molten)
						.setSteamCost(Fluid.BUCKET_VOLUME / 16).setCraftTime(120).build();

				new MachineRecipeBuilder("casting bench").setFluidInputs(molten).setItemOutputs(ingotStack)
						.setCraftTime(2400).build();
			}
			if(!plateStack.isEmpty()) {
				ItemStack plateCopy = plateStack.copy();
				plateCopy.setCount(plateCount);
				new MachineRecipeBuilder("steam hammer").setItemInputs(ingot).setItemOutputs(plateCopy).build();
			}
			if(!gearStack.isEmpty()) {
				// TODO
				// SteamHammerRecipe.addSteamHammerRecipe(ingot, gear, "gear");
			}
			if(!ore.isEmpty()) {
				// TODO: Use 'our' stacks not preferred
				if(FurnaceRecipes.instance().getSmeltingResult(oreStack).isEmpty()) {
					GameRegistry.addSmelting(oreStack, ingotStack, 0.5F);
				}
			}
			if(!dust.isEmpty()) {
				// TODO: Use 'our' stacks not preferred
				if(FurnaceRecipes.instance().getSmeltingResult(dustStack).isEmpty()) {
					GameRegistry.addSmelting(dustStack, ingotStack, 0.5F);
				}
			}
			if(!crushedOreStack.isEmpty()) {
				ItemStack nuggetCopy = nuggetStack.copy();
				nuggetCopy.setCount(3);
				GameRegistry.addSmelting(crushedOreStack, nuggetCopy, 0.1f);
				ItemStack crushedOreCopy = crushedOreStack.copy();
				crushedOreCopy.setCount(4);
				new MachineRecipeBuilder("steam hammer").setItemInputs(ore).setItemOutputs(crushedOreCopy).build();
			}
			if(!crystalStack.isEmpty()) {
				if(!nugget.isEmpty()) {
					GameRegistry.addSmelting(crystalStack, nuggetStack, 0.3f);
				}
				if(solution != null) {
					new MachineRecipeBuilder("vat").setFluidOutputs(solution)
							.setFluidInputs(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME / 4))
							.setItemInputs(crushedOre).build();
					new MachineRecipeBuilder("distiller").setFluidInputs(solution)
							.setFluidOutputs(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME / 6))
							.setItemOutputs(crystalStack).setCraftTime(20).build();
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

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		getConfigRegistry().addEntry("plateCount", new ConfigEntry("balance", "plateCount", Type.INTEGER, "1"));
		plateCount = getConfigRegistry().getInt("plateCount", 1);
		getConfigRegistry().addEntry("dustCount", new ConfigEntry("balance", "dustCount", Type.INTEGER, "1"));
		dustCount = getConfigRegistry().getInt("dustCount", 1);
		getConfigRegistry().addCategoryComment("balance", "Adjust number of items produced in recipes", "General");
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
		itemRegistry.register(new ItemHammer());
		itemRegistry.register(new ItemDie());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

}
