package xyz.brassgoggledcoders.steamagerevolution;

import java.util.*;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.registrysystem.*;
import com.teamacronymcoders.base.registrysystem.config.ConfigEntry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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
import xyz.brassgoggledcoders.steamagerevolution.entities.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.*;
import xyz.brassgoggledcoders.steamagerevolution.items.*;
import xyz.brassgoggledcoders.steamagerevolution.items.tools.*;
import xyz.brassgoggledcoders.steamagerevolution.materials.ModuleMaterials;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.HandlerSetBurnTime;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.PacketSetBurnTime;
import xyz.brassgoggledcoders.steamagerevolution.network.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.LootFunctionOredict;
import xyz.brassgoggledcoders.steamagerevolution.utils.StackComparator;

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

    public static final ToolMaterial STEAM = EnumHelper.addToolMaterial("TOOL_STEAM", 2, -1, 12.0F, 3.0F, 0);
    public static final ArmorMaterial GOGGLES = EnumHelper.addArmorMaterial("ARMOR_GOGGLES", "goggles", -1,
            new int[] { 1, 2, 3, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

    public static final HashSet<Block> KNOWN_ORES = new HashSet<Block>();
    public static ArrayList<ILens> lenseTypes = Lists.newArrayList();

    public static String name = "[" + MODNAME + "]";
    public static GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(name.getBytes()), name);

    public SteamAgeRevolution() {
        super(MODID, MODNAME, MODVERSION, tab, true);
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
        SARBlocks.registerBlocks(blockRegistry);
    }

    @Override
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        this.getLibProxy().addSidedBlockDomain();
        proxy.registerModels();
        // Reminder - Side is the side you're sending to (recieving side)
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerFluidUpdate.class, PacketFluidUpdate.class,
                Side.CLIENT);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerIncreaseHunger.class,
                PacketIncreaseHunger.class, Side.SERVER);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerSetRecipe.class, PacketSetRecipe.class,
                Side.CLIENT);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerGUITankInteract.class,
                PacketGUITankInteract.class, Side.SERVER);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerSetTemperature.class,
                PacketSetTemperature.class, Side.CLIENT);
        SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerSetBurnTime.class, PacketSetBurnTime.class,
                Side.CLIENT);
        SARCaps.register();
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
        ModuleMaterials.knownMetalTypes.add("Iron");
        ModuleMaterials.knownMetalTypes.add("Gold");

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
