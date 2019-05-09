package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class EventHandler {
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(
				new RecipeAddLens().setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "add_lens")));
	}
	
	//NOTE: Handled in ModuleMetalworking
	//public static void onOreRegistered(OreDictionary.OreRegisterEvent event)

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
