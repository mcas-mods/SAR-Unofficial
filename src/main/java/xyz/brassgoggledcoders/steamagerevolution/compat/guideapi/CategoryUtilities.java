package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageIRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;

@ObjectHolder(SteamAgeRevolution.MODID)
public class CategoryUtilities {

	public static final Block fluid_io = null;
	public static final Block trunk = null;
	public static final Block steam_vent = null;
	public static final Item clockwork_wings = null;

	public static Map<ResourceLocation, EntryAbstract> buildCategory() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		String keyBase = "guide." + SteamAgeRevolution.MODID + ".entry.utils.";

		List<IPage> ioEntry = new ArrayList<IPage>();
		ioEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "fluid_io.info")));
		ioEntry.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "fluid_io")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "fluid_io_entry"),
				new EntryItemStack(ioEntry, keyBase + "fluid_io", new ItemStack(fluid_io)));

		List<IPage> steamVentEntry = new ArrayList<IPage>();
		steamVentEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "steam_vent.info")));
		steamVentEntry.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "steam_vent")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "steam_vent"),
				new EntryItemStack(steamVentEntry, keyBase + "steam_vent", new ItemStack(steam_vent)));

		List<IPage> clockworkWingsEntry = new ArrayList<IPage>();
		clockworkWingsEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "clockwork_wings.info")));
		clockworkWingsEntry
				.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "clockwork_wings")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "clockwork_wings"),
				new EntryItemStack(clockworkWingsEntry, keyBase + "clockwork_wings", new ItemStack(clockwork_wings)));

		List<IPage> aestheticEntry = new ArrayList<IPage>();
		aestheticEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "aesthetic.info")));
		aestheticEntry.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "trunk")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "aesthetic_entry"),
				new EntryItemStack(aestheticEntry, keyBase + "aesthetic", new ItemStack(trunk)));

		for(EntryAbstract entry : entries.values()) {
			PageHelper.setPagesToUnicode(entry.pageList);
		}

		return entries;
	}

}
