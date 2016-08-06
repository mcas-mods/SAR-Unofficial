package xyz.brassgoggledcoders.steamagerevolution;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.BaseCreativeTab;
import xyz.brassgoggledcoders.boilerplate.BoilerplateModBase;

@Mod(modid = SteamAgeRevolution.MODID, name = SteamAgeRevolution.MODNAME, version = SteamAgeRevolution.MODVERSION,
		dependencies = SteamAgeRevolution.DEPENDENCIES)
public class SteamAgeRevolution extends BoilerplateModBase {

	static {
		FluidRegistry.enableUniversalBucket();
	}

	public SteamAgeRevolution() {
		super(MODID, MODNAME, MODVERSION, tab);
	}

	@Instance("steamagerevolution")
	public static SteamAgeRevolution instance;

	public static final String MODID = "steamagerevolution";
	public static final String MODNAME = "Steam Age Revolution";
	public static final String MODVERSION = "@VERSION@";
	public static final String DEPENDENCIES = "required-after:boilerplate";

	public static CreativeTabs tab = new SARTab();

	public static DamageSource belt =
			new DamageSource("belt").setDifficultyScaled().setDamageBypassesArmor().setDamageIsAbsolute();

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		CapabilityHandler.init();
		super.preInit(event);
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
	public Object getInstance() {
		return instance;
	}

	public static class SARTab extends BaseCreativeTab {

		public SARTab() {
			super(MODID);
		}

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Blocks.SPONGE);
		}

	}
}
