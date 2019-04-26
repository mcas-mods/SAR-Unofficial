package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import java.util.*;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.entry.EntryItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class CategoryProduction {

	public static Map<ResourceLocation, EntryAbstract> buildCategory() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		String keyBase = "guide." + SteamAgeRevolution.MODID + ".entry.production.";

		BookUtils.addBasicEntry(entries, keyBase, "concepts", new ItemStack(BookObjectHolder.boiler_casing), false);

		List<IPage> boilerEntry = new ArrayList<IPage>();
		boilerEntry.addAll(PageHelper.pagesForLongText(I18n.translateToLocalFormatted(keyBase + "boiler.info")));
		// boilerEntry.add(new PageJsonRecipe(new
		// ResourceLocation(SteamAgeRevolution.MODID, "boiler_casing")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "boiler_entry"),
				new EntryItemStack(boilerEntry, keyBase + "boiler", new ItemStack(BookObjectHolder.boiler_casing)));

		BookUtils.addBasicEntry(entries, keyBase, "crucible", new ItemStack(BookObjectHolder.crucible_casing), true);

		BookUtils.addBasicEntry(entries, keyBase, "alloy_forge", new ItemStack(BookObjectHolder.alloy_furnace_frame),
				true);

		BookUtils.addBasicEntry(entries, keyBase, "steelworks", new ItemStack(BookObjectHolder.steelworks_frame), true);

		BookUtils.addBasicEntry(entries, keyBase, "steam_hammer", new ItemStack(BookObjectHolder.steamhammer_frame),
				true);

		for(EntryAbstract entry : entries.values()) {
			PageHelper.setPagesToUnicode(entry.pageList);
		}

		return entries;
	}

}
