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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;

@ObjectHolder(SteamAgeRevolution.MODID)
public class CategoryProduction {

	public static final Block boiler_casing = null;
	public static final Block crucible_casing = null;
	public static final Block alloy_furnace_frame = null;
	public static final Block steamhammer_frame = null;

	public static Map<ResourceLocation, EntryAbstract> buildCategory() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		String keyBase = "guide." + SteamAgeRevolution.MODID + ".entry.production.";

		List<IPage> boilerEntry = new ArrayList<IPage>();
		boilerEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "boiler.info")));
		boilerEntry.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "boiler_casing")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "boiler_entry"),
				new EntryItemStack(boilerEntry, keyBase + "boiler", new ItemStack(boiler_casing)));

		List<IPage> crucibleEntry = new ArrayList<IPage>();
		crucibleEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "crucible.info")));
		crucibleEntry.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "crucible_casing")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "crucible_entry"),
				new EntryItemStack(crucibleEntry, keyBase + "crucible", new ItemStack(crucible_casing)));

		List<IPage> alloyForgeEntry = new ArrayList<IPage>();
		alloyForgeEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "alloy_forge.info")));
		alloyForgeEntry
				.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "alloy_furnace_frame")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "alloy_forge_entry"),
				new EntryItemStack(alloyForgeEntry, keyBase + "alloy_forge", new ItemStack(alloy_furnace_frame)));

		List<IPage> hammerEntry = new ArrayList<IPage>();
		hammerEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "steam_hammer.info")));
		hammerEntry.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "steamhammer_frame")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "steam_hammer_entry"),
				new EntryItemStack(hammerEntry, keyBase + "steam_hammer", new ItemStack(steamhammer_frame)));

		for(EntryAbstract entry : entries.values()) {
			PageHelper.setPagesToUnicode(entry.pageList);
		}

		return entries;
	}

}
