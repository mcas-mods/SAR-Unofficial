package xyz.brassgoggledcoders.steamagerevolution;

import java.awt.Color;
import java.util.*;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.blocks.BlockFluidBase;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.registrysystem.*;
import com.teamacronymcoders.base.registrysystem.config.ConfigEntry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.api.ILens;
import xyz.brassgoggledcoders.steamagerevolution.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.entities.*;
import xyz.brassgoggledcoders.steamagerevolution.items.*;
import xyz.brassgoggledcoders.steamagerevolution.items.tools.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblock.vat.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.tank.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.network.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.LootFunctionOredict;
import xyz.brassgoggledcoders.steamagerevolution.utils.StackComparator;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.BlockDamagingFluid;

@Mod(modid = SteamAgeRevolution.MODID, name = SteamAgeRevolution.MODNAME, version = SteamAgeRevolution.MODVERSION, dependencies = SteamAgeRevolution.DEPENDENCIES)
@EventBusSubscriber
public class SteamAgeRevolution extends BaseModFoundation<SteamAgeRevolution> {

	public static final String MODID = "steamagerevolution";
	public static final String MODNAME = "Steam Age Revolution";
	public static final String MODVERSION = "@VERSION@";
	// TODO Versioned BASE Dependency
	public static final String DEPENDENCIES = "required-after:base;before:guideapi@[1.12-2.1.4-60,)";
	@Instance("steamagerevolution")
	public static SteamAgeRevolution instance;
	public static CreativeTabs tab = new SARTab();
	@SidedProxy(clientSide = "xyz.brassgoggledcoders.steamagerevolution.proxies.ClientProxy", serverSide = "xyz.brassgoggledcoders.steamagerevolution.proxies.CommonProxy")
	public static xyz.brassgoggledcoders.steamagerevolution.proxies.CommonProxy proxy;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	// TODO Difficulty scaling. The default method results in no damage on peaceful.
	public static DamageSource damageSourceGas = new DamageSource("gas").setDamageIsAbsolute();
	public static DamageSource damageSourceAcid = new DamageSource("acid").setDamageIsAbsolute();
	public static DamageSource damageSourceBullet = new DamageSource("bullet").setProjectile();
	public static DamageSource damageSourceGrinder = new DamageSource("grinder").setDamageBypassesArmor()
			.setDamageIsAbsolute();
	public static DamageSource damageSourceHammer = new DamageSource("hammer").setDamageBypassesArmor()
			.setDamageIsAbsolute();

	public static final ToolMaterial STEAM = EnumHelper.addToolMaterial("TOOL_STEAM", 2, -1, 12.0F, 3.0F, 0);
	public static final ArmorMaterial GOGGLES = EnumHelper.addArmorMaterial("ARMOR_GOGGLES", "goggles", -1,
			new int[] { 1, 2, 3, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

	public static final HashSet<Block> KNOWN_ORES = new HashSet<Block>();
	public static List<String> knownMetalTypes = Lists.newArrayList();
	public static ArrayList<ILens> lenseTypes = Lists.newArrayList();

	public static String name = "[" + MODNAME + "]";
	public static GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(name.getBytes()), name);

	public SteamAgeRevolution() {
		super(MODID, MODNAME, MODVERSION, tab, true);
	}

	public static FluidStack getPotionFluidStack(String potionType, int amount) {
		FluidStack potion = FluidRegistry.getFluidStack("potion", amount);
		potion.tag = new NBTTagCompound();
		potion.tag.setString("Potion", potionType);
		return potion;
	}

	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityEntry> entityRegistryEvent) {
		EntityRegistry entityRegistry = SteamAgeRevolution.instance.getRegistry(EntityRegistry.class, "ENTITY");
		entityRegistry.register(EntityDeadInventor.class);
		net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(EntityDeadInventor.class, 10, 1, 2,
				EnumCreatureType.MONSTER, Biomes.DESERT, Biomes.ROOFED_FOREST, Biomes.JUNGLE, Biomes.EXTREME_HILLS);// TODO
		entityRegistry.register(EntityMinecartCarrier.class);
		entityRegistry.register(EntityMinecartDrilling.class);
	}

	@Override
	public void registerItems(ItemRegistry itemRegistry) {
		itemRegistry.register(new ItemCanister("canister", Fluid.BUCKET_VOLUME * 8));

		itemRegistry.register(new ItemBase("charcoal_powder"));
		itemRegistry.register(new ItemFlask("flask", Fluid.BUCKET_VOLUME * 3));

		itemRegistry.register(new ItemMinecartCarrier());
		itemRegistry.register(new ItemMinecartDrilling());

		itemRegistry.register(new ItemClockworkWings());

		itemRegistry.register(new ItemSteamPickaxe("steam_pickaxe", 1000));
		itemRegistry.register(new ItemSteamAxe("steam_axe", 1000));
		itemRegistry.register(new ItemSteamShovel("steam_shovel", 1000));
		itemRegistry.register(new ItemSteamHoe("steam_hoe", 1000));
		itemRegistry.register(new ItemSteamSword("steam_sword", 1000));

		itemRegistry.register(new ItemRocketFist());

		itemRegistry.register(new ItemBase("drill_base"));
		itemRegistry.register(new ItemDrill("stone_drill", ToolMaterial.STONE));
		itemRegistry.register(new ItemDrill("iron_drill", ToolMaterial.IRON));
		itemRegistry.register(new ItemDrill("gold_drill", ToolMaterial.GOLD));
		itemRegistry.register(new ItemDrill("diamond_drill", ToolMaterial.DIAMOND));
		// Steel tools?
		// TODO Add drills from other mods materials and ability for other mods to
		// register drills through IMC

		itemRegistry.register(new ItemLens());
		itemRegistry.register(new ItemGoggles());

		itemRegistry.register(new ItemEntrenchingTool("entrenching_tool", ToolMaterial.IRON));

		itemRegistry.register(new ItemHammer());
		itemRegistry.register(new ItemDie());
	}

	@Override
	public void registerBlocks(BlockRegistry blockRegistry) {
		blockRegistry.register(new BlockPneumaticTube(Material.IRON, "pneumatic_tube"));
		blockRegistry.register(new BlockPneumaticSender(Material.IRON, "pneumatic_sender"));
		blockRegistry.register(new BlockPneumaticRouter(Material.IRON, "pneumatic_router"));

		// TODO Adjust properties
		Fluid steam = new Fluid("steam", new ResourceLocation(SteamAgeRevolution.MODID, "fluids/steam"),
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/steam_flow")).setGaseous(true)
						.setTemperature(1000).setViscosity(200);

		if(!(FluidRegistry.isFluidRegistered(steam))) { // Soft registration
			FluidRegistry.registerFluid(steam);
			FluidRegistry.addBucketForFluid(steam);
		}
		blockRegistry.register(new BlockFluidBase("steam", FluidRegistry.getFluid("steam"), Material.LAVA) {
			@Override
			public ResourceLocation getResourceLocation(IBlockState blockState) {
				return new ResourceLocation(SteamAgeRevolution.MODID, "steam");
			}
		});

		blockRegistry.register(new BlockBoilerCasing(Material.IRON, "boiler_casing"));
		blockRegistry.register(new BlockBoilerWaterInput(Material.IRON, "boiler_water_input"));
		blockRegistry.register(new BlockBoilerSteamOutput(Material.IRON, "boiler_steam_output"));
		blockRegistry.register(new BlockBoilerSolidFirebox(Material.IRON, "boiler_solid_firebox"));
		blockRegistry.register(new BlockBoilerLiquidFirebox(Material.IRON, "boiler_liquid_firebox"));
		blockRegistry.register(new BlockBoilerGauge(Material.IRON, "boiler_gauge"));
		blockRegistry.register(new BlockBoilerPressureMonitor(Material.IRON, "boiler_pressuremonitor"));
		blockRegistry.register(new BlockBoilerPressureValve(Material.IRON, "boiler_pressurevalve"));

		blockRegistry.register(new BlockSteamVent(Material.IRON, "steam_vent"));

		blockRegistry.register(new BlockCharcoal());

		blockRegistry.register(new BlockFurnaceCasing(Material.IRON, "furnace_casing"));
		blockRegistry.register(new BlockFurnaceItemInput(Material.IRON, "furnace_item_input"));
		blockRegistry.register(new BlockFurnaceItemOutput(Material.IRON, "furnace_item_output"));
		blockRegistry.register(new BlockFurnaceSteamInput(Material.IRON, "furnace_steam_input"));

		blockRegistry.register(new BlockGrinderFrame());
		blockRegistry.register(new BlockGrinderInput());
		blockRegistry.register(new BlockGrinderOutput());

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

		blockRegistry.register(new BlockVatFrame(Material.IRON, "vat_frame"));
		blockRegistry.register(new BlockVatFluidInput(Material.IRON, "vat_fluid_input"));
		blockRegistry.register(new BlockVatOutput(Material.IRON, "vat_output"));

		blockRegistry.register(new BlockFumeCollector(Material.IRON, "fume_collector"));

		blockRegistry.register(new BlockDistillerFluidInput(Material.IRON, "distiller_fluid_input"));
		blockRegistry.register(new BlockDistillerFluidOutput(Material.IRON, "distiller_fluid_output"));
		blockRegistry.register(new BlockDistillerFrame(Material.IRON, "distiller_frame"));
		blockRegistry.register(new BlockDistillerHotplate(Material.IRON, "distiller_hotplate"));
		blockRegistry.register(new BlockDistillerRadiator(Material.IRON, "distiller_radiator"));
		blockRegistry.register(new BlockDistillerItemOutput(Material.IRON, "distiller_item_output"));

		blockRegistry.register(new BlockTrunk(Material.WOOD, "trunk"));
		blockRegistry.register(new BlockFluidIO());
		blockRegistry.register(new BlockFluidHopper(Material.IRON, "fluid_hopper"));
		blockRegistry.register(new BlockTankCasing(Material.ROCK, "tank_casing"));
		blockRegistry.register(new BlockTankValve(Material.IRON, "tank_valve"));
		blockRegistry.register(new BlockTankWindow(Material.GLASS, "tank_window"));

		Fluid sulphur_dioxide = new Fluid("sulphur_dioxide",
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphur_dioxide"),
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphur_dioxide_flow")).setViscosity(250)
						.setGaseous(true).setDensity(-100);
		FluidRegistry.registerFluid(sulphur_dioxide);
		FluidRegistry.addBucketForFluid(sulphur_dioxide);

		blockRegistry.register(new BlockDamagingFluid("sulphur_dioxide", FluidRegistry.getFluid("sulphur_dioxide"),
				Material.WATER, SteamAgeRevolution.damageSourceGas, 2));

		Fluid sulphuric_acid = new Fluid("sulphuric_acid",
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphuric_acid"),
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphuric_acid_flow")).setViscosity(500);
		FluidRegistry.registerFluid(sulphuric_acid);
		FluidRegistry.addBucketForFluid(sulphuric_acid);

		blockRegistry.register(new BlockDamagingFluid("sulphuric_acid", FluidRegistry.getFluid("sulphuric_acid"),
				Material.WATER, SteamAgeRevolution.damageSourceAcid, 4) {
			@Override
			public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state,
					@Nonnull Random rand) {
				if(!world.isRemote) {
					for(EnumFacing facing : EnumFacing.VALUES) {
						if(rand.nextInt(10) == 0) {
							BlockPos other = pos.offset(facing);
							Material mat = world.getBlockState(other).getMaterial();
							if(Material.GROUND.equals(mat) || Material.GRASS.equals(mat) || Material.ROCK.equals(mat)) {
								world.setBlockToAir(other);
							}
						}
					}
				}
				super.updateTick(world, pos, state, rand);
			}
		});

		// TODO TE compat?
		Fluid liquid_glowstone = new Fluid("liquid_glowstone",
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/liquid_glowstone"),
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/liquid_glowstone_flow")).setViscosity(2000)
						.setGaseous(true);
		FluidRegistry.registerFluid(liquid_glowstone);
		FluidRegistry.addBucketForFluid(liquid_glowstone);

		blockRegistry.register(
				new BlockFluidBase("liquid_glowstone", FluidRegistry.getFluid("liquid_glowstone"), Material.LAVA) {
					@Override
					public ResourceLocation getResourceLocation(IBlockState blockState) {
						return new ResourceLocation(SteamAgeRevolution.MODID, "liquid_glowstone");
					}

					@Override
					public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
						if(entityIn instanceof EntityLiving) {
							EntityLiving living = (EntityLiving) entityIn;
							living.addPotionEffect(new PotionEffect(
									Potion.getPotionFromResourceLocation("minecraft:glowing"), 20, 1, true, false));
						}
					}
				}.setLightLevel(0.8F));

		Fluid potion = new Fluid("potion", new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution"),
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution_flowing")) {
			@Override
			public String getLocalizedName(FluidStack stack) {
				return I18n.translateToLocal(
						PotionUtils.getPotionTypeFromNBT(stack.tag).getNamePrefixed("potion.effect."));
			}

			@Override
			public int getColor(FluidStack stack) {
				return PotionUtils.getPotionColorFromEffectList(PotionUtils.getEffectsFromTag(stack.tag));
			}
		};
		FluidRegistry.registerFluid(potion);
		FluidRegistry.addBucketForFluid(potion);

		FluidRegistry
				.registerFluid(new Fluid("slime", new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution"),
						new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution")) {
					@Override
					public int getColor() {
						return Color.GREEN.getRGB();
					}
				});
	}

	@Override
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		this.getLibProxy().addSidedBlockDomain();
		proxy.registerModels();
		SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerFluidUpdate.class, PacketFluidUpdate.class,
				Side.CLIENT);
		SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerMultiFluidUpdate.class,
				PacketMultiFluidUpdate.class, Side.CLIENT);
		SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerItemUpdate.class, PacketItemUpdate.class,
				Side.CLIENT);
		SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerIncreaseHunger.class,
				PacketIncreaseHunger.class, Side.SERVER);
		SARCapabilities.register();
		proxy.preInit(event);
		SteamAgeRevolution.instance.getRegistry(ConfigRegistry.class, "CONFIG").addEntry("plateCount",
				new ConfigEntry("balance", "plateCount", Type.INTEGER, "1"));
		SARRecipes.plateCount = SteamAgeRevolution.instance.getRegistry(ConfigRegistry.class, "CONFIG")
				.getInt("plateCount", 1);
		SteamAgeRevolution.instance.getRegistry(ConfigRegistry.class, "CONFIG").addEntry("dustCount",
				new ConfigEntry("balance", "dustCount", Type.INTEGER, "1"));
		SARRecipes.dustCount = SteamAgeRevolution.instance.getRegistry(ConfigRegistry.class, "CONFIG")
				.getInt("dustCount", 1);
		SteamAgeRevolution.instance.getRegistry(ConfigRegistry.class, "CONFIG").addCategoryComment("balance",
				"Adjust number of items produced in recipes", "General");
		SteamAgeRevolution.knownMetalTypes.add("Iron");
		SteamAgeRevolution.knownMetalTypes.add("Gold");

		for(String material : ModuleMaterials.heavyOreMaterials) {
			LootTableList.register(new ResourceLocation(SteamAgeRevolution.MODID, "heavy_ore_" + material));
		}
		LootFunctionManager.registerFunction(new LootFunctionOredict.Serializer());

	}

	@Override
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		OreDictionary.registerOre("blockCharcoal", SARObjectHolder.charcoal_block);
		OreDictionary.registerOre("dustCharcoal", SARObjectHolder.charcoal_powder);
		OreDictionary.registerOre("listAllmeatraw", Items.CHICKEN);
		OreDictionary.registerOre("listAllmeatraw", Items.BEEF);
		OreDictionary.registerOre("listAllmeatraw", Items.PORKCHOP);
		OreDictionary.registerOre("listAllmeatraw", Items.MUTTON);
		OreDictionary.registerOre("listAllmeatraw", Items.RABBIT);
		proxy.init(event);
	}

	@Override
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		proxy.postInit(event);
	}

	@Override
	public SteamAgeRevolution getInstance() {
		return instance;
	}

	public static class SARTab extends CreativeTabs {

		public SARTab() {
			super(MODID);
		}

		@Override
		public ItemStack createIcon() {
			return new ItemStack(SARObjectHolder.hammer);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> items) {
			super.displayAllRelevantItems(items);
			items.sort(new StackComparator());
		}
	}
}
