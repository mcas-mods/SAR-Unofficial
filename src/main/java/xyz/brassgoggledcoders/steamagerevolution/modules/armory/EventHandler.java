package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.entities.EntityBullet;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.entities.EntityDeadInventor;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.ItemClockworkWings;

@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class EventHandler {
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(
				new RecipeAddLens().setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "add_lens")));
	}
	
	//NOTE: Handled in ModuleMetalworking
	//public static void onOreRegistered(OreDictionary.OreRegisterEvent event)

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		int networkID = 0;
		EntityRegistry.registerModEntity(new ResourceLocation(SteamAgeRevolution.MODID, "bullet"), EntityBullet.class,
				"bullet", networkID++, SteamAgeRevolution.MODID, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(SteamAgeRevolution.MODID, "dead_inventor"),
				EntityDeadInventor.class, "dead_inventor", networkID++, SteamAgeRevolution.MODID, 64, 1, true, 0,
				11111);
		EntityRegistry.addSpawn(EntityDeadInventor.class, 10, 1, 2, EnumCreatureType.MONSTER, Biomes.DESERT,
				Biomes.ROOFED_FOREST, Biomes.JUNGLE, Biomes.EXTREME_HILLS);// TODO
	}

	//No fall damage while wearing wings
	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) { 
		if(event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if((player.getFoodStats().getFoodLevel() != 0) && player.inventory.armorInventory.get(2).getItem() == ModuleArmory.wings) {		
				//player.addExhaustion(ItemClockworkWings.hungerPerTick / 4); TODO
				event.setDamageMultiplier(0);	
			}
		}
	}
}
