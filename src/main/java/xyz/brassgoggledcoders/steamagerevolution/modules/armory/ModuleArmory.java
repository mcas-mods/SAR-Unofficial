package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.blocks.BlockGunsmithingBench;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.entities.EntityBullet;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IAmmo.AmmoType;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IMechanism.ActionType;

@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber
public class ModuleArmory extends ModuleBase {

	public static final ToolMaterial STEAM = EnumHelper.addToolMaterial("TOOL_STEAM", 2, -1, 12.0F, 3.0F, 0);

	public static DamageSource damageSourceBullet = new DamageSource("bullet").setDifficultyScaled().setProjectile();

	@ObjectHolder(SteamAgeRevolution.MODID + ":gun")
	public static final Item gun = null;

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		int networkID = 0;
		EntityRegistry.registerModEntity(new ResourceLocation(SteamAgeRevolution.MODID, "bullet"), EntityBullet.class,
				"Bullet", networkID++, SteamAgeRevolution.MODID, 64, 1, true);
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		blockRegistry.register(new BlockGunsmithingBench());
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		// itemRegistry.register(new ItemExpandableArmor(ArmorMaterial.CHAIN,
		// EntityEquipmentSlot.HEAD,
		// "brass_helmet"));
		itemRegistry.register(new ItemClockworkWings());

		itemRegistry.register(new ItemSteamPickaxe("steam_pickaxe", 1000));
		itemRegistry.register(new ItemSteamAxe("steam_axe", 1000));
		itemRegistry.register(new ItemSteamShovel("steam_shovel", 1000));
		itemRegistry.register(new ItemSteamHoe("steam_hoe", 1000));
		itemRegistry.register(new ItemSteamSword("steam_sword", 1000));

		itemRegistry.register(new ItemGun());
		itemRegistry.register(new ItemAmmo("iron_ball", AmmoType.BALL, 2));
		itemRegistry.register(new ItemAmmo("cartridge", AmmoType.CARTRIDGE, 5));
		itemRegistry.register(new ItemMechanism("bolt_trigger", ActionType.BOLT));
		itemRegistry.register(new ItemMechanism("semi_trigger", ActionType.SEMI));
		itemRegistry.register(new ItemMechanism("auto_trigger", ActionType.AUTO));
		itemRegistry.register(new ItemChamber("ball_chamber", AmmoType.BALL));
		itemRegistry.register(new ItemChamber("cartidge_chamber", AmmoType.CARTRIDGE));
		itemRegistry.register(new ItemAmmoContainer("cartridge_clip", 5, AmmoType.CARTRIDGE));
	}

	@Override
	public String getName() {
		return "Armoury";
	}
}
