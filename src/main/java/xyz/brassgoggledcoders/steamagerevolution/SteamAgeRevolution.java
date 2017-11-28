package xyz.brassgoggledcoders.steamagerevolution;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;

import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import com.teamacronymcoders.base.util.Platform;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;
import xyz.brassgoggledcoders.steamagerevolution.network.*;

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
	// TODO Versioned BASE Dependency
	public static final String DEPENDENCIES = "required-after:base;before:guideapi@[1.12-2.1.4-57,)";

	public static CreativeTabs tab = new SARTab();

	@SidedProxy(clientSide = "xyz.brassgoggledcoders.steamagerevolution.proxies.ClientProxy",
			serverSide = "xyz.brassgoggledcoders.steamagerevolution.proxies.CommonProxy")
	public static xyz.brassgoggledcoders.steamagerevolution.proxies.CommonProxy proxy;

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		proxy.registerModels();
		SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerFluidUpdate.class, PacketFluidUpdate.class,
				Side.CLIENT);
		SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerMultiFluidUpdate.class,
				PacketMultiFluidUpdate.class, Side.CLIENT);
		SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerItemUpdate.class, PacketItemUpdate.class,
				Side.CLIENT);
		SARCapabilities.register();
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

	@Override
	public File getResourceFolder() {
		if(!Platform.isDevEnv())
			return null;
		else {
			return new File(
					this.getRegistry(ConfigRegistry.class, "CONFIG").getConfigFolder().getParentFile().getParentFile()
							.getParentFile().getParentFile().getPath(),
					"src/main/resources/assets/" + this.getID() + "/");
		}
	}

	public static class SARTab extends CreativeTabs {

		public SARTab() {
			super(MODID);
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModuleMetalworking.hammer);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(NonNullList<ItemStack> items) {
			super.displayAllRelevantItems(items);
			Collections.sort(items, new StackComparator());
		}

		class StackComparator implements Comparator<ItemStack> {

			@Override
			public int compare(ItemStack o1, ItemStack o2) {
				return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
			}
		}

	}
}
