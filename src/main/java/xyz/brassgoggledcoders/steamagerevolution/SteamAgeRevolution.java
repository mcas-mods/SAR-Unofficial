package xyz.brassgoggledcoders.steamagerevolution;

import me.modmuss50.jsonDestroyer.JsonDestroyer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.BoilerplateLib;
import xyz.brassgoggledcoders.boilerplate.lib.common.BaseCreativeTab;
import xyz.brassgoggledcoders.boilerplate.lib.common.IBoilerplateMod;
import xyz.brassgoggledcoders.boilerplate.lib.common.utils.ModLogger;
import xyz.brassgoggledcoders.steamagerevolution.init.InitItems;

@Mod(modid = SteamAgeRevolution.MODID, name = SteamAgeRevolution.MODNAME, version = SteamAgeRevolution.MODVERSION)
public class SteamAgeRevolution implements IBoilerplateMod
{
	@Instance("steamagerevolution")
	public static SteamAgeRevolution instance;

	public static final String MODID = "steamagerevolution";
	public static final String MODNAME = "Steam Age Revolution";
	public static final String MODVERSION = "@VERSION@";

	public static JsonDestroyer jsonDestroyer = new JsonDestroyer();

	public CreativeTabs tab = new SARTab();

	public static ModLogger logger;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = new ModLogger(MODID);
		BoilerplateLib.getInstance().preInitStart(event);
		logger = BoilerplateLib.getLogger();
		InitItems.init();
		BoilerplateLib.getInstance().preInitEnd(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		BoilerplateLib.getInstance().init(event);
		jsonDestroyer.load();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		BoilerplateLib.getInstance().postInit(event);
	}

	@Override
	public Object getInstance()
	{
		return instance;
	}

	@Override
	public CreativeTabs getCreativeTab()
	{
		return tab;
	}

	@Override
	public String getID()
	{
		return MODID;
	}

	@Override
	public String getName()
	{
		return MODNAME;
	}

	@Override
	public String getVersion()
	{
		return MODVERSION;
	}

	@Override
	public String getPrefix()
	{
		return MODID + ":";
	}

	@Override
	public ModLogger getLogger()
	{
		return logger;
	}

	@Override
	public String getClientProxyPath()
	{
		return null;
	}

	@Override
	public String getCommonProxyPath()
	{
		return null;
	}

	public class SARTab extends BaseCreativeTab
	{

		public SARTab()
		{
			super(SteamAgeRevolution.MODID + "_tab");
		}

		@Override
		public Item getTabIconItem()
		{
			return InitItems.item_plates;
		}

	}

	@Override
	public Configuration getConfig() {
		return null;
	}

}
