package xyz.brassgoggledcoders.steamagerevolution;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.BaseModFoundation;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;
import xyz.brassgoggledcoders.steamagerevolution.network.HandlerCardPunch;
import xyz.brassgoggledcoders.steamagerevolution.network.HandlerFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.HandlerGunCraft;
import xyz.brassgoggledcoders.steamagerevolution.network.HandlerItemUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.HandlerMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketCardPunch;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketGunCraft;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketItemUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.StackComparator;

@Mod(modid = SteamAgeRevolution.MODID, name = SteamAgeRevolution.MODNAME, version = SteamAgeRevolution.MODVERSION, dependencies = SteamAgeRevolution.DEPENDENCIES)
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

	public SteamAgeRevolution() {
		super(MODID, MODNAME, MODVERSION, tab, true);
	}

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
		SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerCardPunch.class, PacketCardPunch.class,
				Side.SERVER);
		SteamAgeRevolution.instance.getPacketHandler().registerPacket(HandlerGunCraft.class, PacketGunCraft.class,
				Side.SERVER);
		SARCapabilities.register();
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe",
				"xyz.brassgoggledcoders.steamagerevolution.compat.oneprobe.OneProbeCompat");
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
		public ItemStack createIcon() {
			return new ItemStack(ModuleMetalworking.hammer);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> items) {
			super.displayAllRelevantItems(items);
			items.sort(new StackComparator());
		}
	}
}
