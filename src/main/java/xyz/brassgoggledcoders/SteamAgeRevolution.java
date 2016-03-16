package xyz.brassgoggledcoders;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.BoilerplateLib;
import xyz.brassgoggledcoders.boilerplate.lib.common.IBoilerplateMod;
import xyz.brassgoggledcoders.boilerplate.lib.common.utils.ModLogger;

@Mod(modid = SteamAgeRevolution.MODID, name = SteamAgeRevolution.MODNAME, version = SteamAgeRevolution.MODVERSION)
public class SteamAgeRevolution implements IBoilerplateMod
{
	@Instance("steamagerevolution")
	public static SteamAgeRevolution instance;

	public static final String MODID = "steamagerevolution";
	public static final String MODNAME = "Steam Age Revolution";
	public static final String MODVERSION = "@VERSION@";

	public static ModLogger logger;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		BoilerplateLib.getInstance().preInitStart(event);
		logger = BoilerplateLib.getLogger();
		BoilerplateLib.getInstance().preInitEnd(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		BoilerplateLib.getInstance().init(event);
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
		return null;
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
	public Configuration getConfig()
	{
		return null;
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
}
