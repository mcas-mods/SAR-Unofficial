package xyz.brassgoggledcoders.steamagerevolution;

import com.teamacronymcoders.base.BaseModFoundation;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SteamAgeRevolution.MODID, name = SteamAgeRevolution.MODNAME, version = SteamAgeRevolution.MODVERSION,
		dependencies = SteamAgeRevolution.DEPENDENCIES)
public class SteamAgeRevolution extends BaseModFoundation<SteamAgeRevolution> {

	static {
		FluidRegistry.enableUniversalBucket();
	}

	public SteamAgeRevolution() {
		super(MODID, MODNAME, MODVERSION, tab, true);
	}

	@Instance("steamagerevolution")
	public static SteamAgeRevolution instance;

	public static final String MODID = "steamagerevolution";
	public static final String MODNAME = "Steam Age Revolution";
	public static final String MODVERSION = "@VERSION@";
	public static final String DEPENDENCIES = "required-after:base";

	public static CreativeTabs tab = new SARTab();

	@SidedProxy(clientSide = "xyz.brassgoggledcoders.steamagerevolution.proxies.ClientProxy",
			serverSide = "xyz.brassgoggledcoders.steamagerevolution.proxies.CommonProxy")
	public static xyz.brassgoggledcoders.steamagerevolution.proxies.CommonProxy proxy;

	// public static DamageSource belt =
	// new DamageSource("belt").setDifficultyScaled().setDamageBypassesArmor().setDamageIsAbsolute();

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		CapabilityHandler.init();
		super.preInit(event);
		proxy.registerModels();
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
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
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(Blocks.SPONGE));
		}

	}
}
