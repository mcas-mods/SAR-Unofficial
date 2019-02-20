package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ILens.VanillaLens;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.entities.EntityBullet;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.entities.EntityRocketSkeleton;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.*;

@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber
public class ModuleArmory extends ModuleBase {

	public static final ToolMaterial STEAM = EnumHelper.addToolMaterial("TOOL_STEAM", 2, -1, 12.0F, 3.0F, 0);

	public static final ArmorMaterial GOGGLES = EnumHelper.addArmorMaterial("ARMOR_GOGGLES", "goggles", -1,
			new int[] { 1, 2, 3, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

	public static DamageSource damageSourceBullet = new DamageSource("bullet").setDifficultyScaled().setProjectile();

	@ObjectHolder(SteamAgeRevolution.MODID + ":gun")
	public static final Item gun = null;

	@ObjectHolder(SteamAgeRevolution.MODID + ":rocket_fist")
	public static final Item rocket_fist = null;

	@ObjectHolder(SteamAgeRevolution.MODID + ":lens")
	public static final Item lens = null;

	@ObjectHolder(SteamAgeRevolution.MODID + ":goggles")
	public static final Item goggles = null;

	public static final HashSet<Block> KNOWN_ORES = new HashSet<Block>();

	public static ArrayList<ILens> lenseTypes = Lists.newArrayList();

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.armory.ClientProxy";
	}

	@SubscribeEvent
	public static void onOreRegistered(OreDictionary.OreRegisterEvent event) {
		if(event.getName().contains("ore")) {
			KNOWN_ORES.add(Block.getBlockFromItem(event.getOre().getItem()));
		}
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		int networkID = 0;
		EntityRegistry.registerModEntity(new ResourceLocation(SteamAgeRevolution.MODID, "bullet"), EntityBullet.class,
				"bullet", networkID++, SteamAgeRevolution.MODID, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(SteamAgeRevolution.MODID, "rocket_skeleton"),
				EntityRocketSkeleton.class, "rocket_skeleton", networkID++, SteamAgeRevolution.MODID, 64, 1, true, 0,
				11111);
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		// blockRegistry.register(new BlockGunsmithingBench());
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
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

		for(int i = 0; i < 16; ++i) {
			if(i == EnumDyeColor.BLUE.getMetadata()) {
				ModuleArmory.lenseTypes.add(new VanillaLens(i) {
					// TODO Switch to a brightness increaser instead of a potion effect
					@Override
					public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
						player.addPotionEffect(
								new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"), 20));
					}
				});
			}
			else {
				ModuleArmory.lenseTypes.add(new VanillaLens(i));
			}
		}

		/*
		 * itemRegistry.register(new ItemGun()); itemRegistry.register(new
		 * ItemAmmo("iron_ball", AmmoType.BALL, 2)); itemRegistry.register(new
		 * ItemAmmo("cartridge", AmmoType.CARTRIDGE, 5)); itemRegistry.register(new
		 * ItemMechanism("bolt_mechanism", ActionType.BOLT) {
		 *
		 * @Override public ActionResult<ItemStack> onItemRightClick(World worldIn,
		 * EntityPlayer playerIn, EnumHand handIn) { ItemStack stack =
		 * playerIn.getHeldItem(handIn);
		 * if(GunUtils.getOrCreateTagCompound(stack).getBoolean("isLoaded")) {
		 * GunUtils.shoot(worldIn, playerIn, stack);
		 * stack.getTagCompound().setBoolean("isLoaded", false); } return
		 * super.onItemRightClick(worldIn, playerIn, handIn); } });
		 * itemRegistry.register(new ItemMechanism("semi_mechanism", ActionType.SEMI) {
		 *
		 * @Override public ActionResult<ItemStack> onItemRightClick(World worldIn,
		 * EntityPlayer playerIn, EnumHand handIn) { ItemStack stack =
		 * playerIn.getHeldItem(handIn);
		 * if(GunUtils.getOrCreateTagCompound(stack).getBoolean("isLoaded")) {
		 * GunUtils.shoot(worldIn, playerIn, stack); ItemStack ammo =
		 * GunUtils.findAmmo(playerIn, stack); if(!ammo.isEmpty()) { ammo.shrink(1);
		 * GunUtils.getOrCreateTagCompound(stack).setBoolean("isLoaded", true); } }
		 * return super.onItemRightClick(worldIn, playerIn, handIn); } });
		 * itemRegistry.register(new ItemMechanism("auto_mechanism", ActionType.AUTO) {
		 *
		 * @Override public ActionResult<ItemStack> onItemRightClick(World worldIn,
		 * EntityPlayer playerIn, EnumHand handIn) { playerIn.setActiveHand(handIn);
		 * return new ActionResult<ItemStack>(EnumActionResult.PASS,
		 * playerIn.getHeldItem(handIn)); }
		 *
		 * @Override public void onUsingTick(ItemStack stack, EntityLivingBase
		 * entityLiving, int count) { ItemStack ammo = GunUtils.findAmmo((EntityPlayer)
		 * entityLiving, stack); if(!ammo.isEmpty()) { ammo.shrink(1);
		 * GunUtils.shoot(entityLiving.getEntityWorld(), entityLiving, stack); } } });
		 * itemRegistry.register(new ItemBarrel("short_barrel", 0, 0));
		 * itemRegistry.register(new ItemBarrel("blunderbuss_barrel", -1.5F, 10));
		 * itemRegistry.register(new ItemStock("standard_stock", 0.7F));
		 * itemRegistry.register(new ItemStock("heavy_stock", 0.9F)); // TODO Slime
		 * stock itemRegistry.register(new ItemChamber("ball_chamber", AmmoType.BALL));
		 * itemRegistry.register(new ItemChamber("cartidge_chamber",
		 * AmmoType.CARTRIDGE)); // itemRegistry.register(new
		 * ItemAmmoContainer("cartridge_clip", 5, // AmmoType.CARTRIDGE));
		 *
		 */
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(
				new RecipeAddLens().setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "add_lens")));
	}

	@Override
	public String getName() {
		return "Armoury";
	}
}
