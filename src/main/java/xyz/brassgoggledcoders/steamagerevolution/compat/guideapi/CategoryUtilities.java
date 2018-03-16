package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import java.util.*;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageJsonRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;

public class CategoryUtilities {

	public static Map<ResourceLocation, EntryAbstract> buildCategory() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		String keyBase = "guide." + SteamAgeRevolution.MODID + ".entry.utils.";

		List<IPage> ioEntry = new ArrayList<IPage>();
		ioEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "fluid_io.info")));
		ioEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "fluid_io")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "fluid_io_entry"),
				new EntryItemStack(ioEntry, keyBase + "fluid_io", new ItemStack(BookObjectHolder.fluid_io)));

		List<IPage> steamVentEntry = new ArrayList<IPage>();
		steamVentEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "steam_vent.info")));
		steamVentEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "steam_vent")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "steam_vent"),
				new EntryItemStack(steamVentEntry, keyBase + "steam_vent", new ItemStack(BookObjectHolder.steam_vent)));

		List<IPage> clockworkWingsEntry = new ArrayList<IPage>();
		clockworkWingsEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "clockwork_wings.info")));
		clockworkWingsEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "clockwork_wings")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "clockwork_wings"), new EntryItemStack(
				clockworkWingsEntry, keyBase + "clockwork_wings", new ItemStack(BookObjectHolder.clockwork_wings)));

		List<IPage> steamToolsEntry = new ArrayList<IPage>();
		steamToolsEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "steam_tools.info")));
		steamToolsEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "steam_sword")));
		steamToolsEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "steam_pickaxe")));
		steamToolsEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "steam_axe")));
		steamToolsEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "steam_shovel")));
		steamToolsEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "steam_hoe")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "steam_tools"), new EntryItemStack(steamToolsEntry,
				keyBase + "steam_tools", new ItemStack(BookObjectHolder.steam_pickaxe)));

		List<IPage> aestheticEntry = new ArrayList<IPage>();
		aestheticEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "aesthetic.info")));
		aestheticEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "trunk")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "aesthetic_entry"),
				new EntryItemStack(aestheticEntry, keyBase + "aesthetic", new ItemStack(BookObjectHolder.trunk)));

		for(EntryAbstract entry : entries.values()) {
			PageHelper.setPagesToUnicode(entry.pageList);
		}

		return entries;
	}

}
