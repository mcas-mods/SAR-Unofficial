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

public class CategoryTransportStorage {

	public static Map<ResourceLocation, EntryAbstract> buildCategory() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		String keyBase = "guide." + SteamAgeRevolution.MODID + ".entry.transportstorage.";

		List<IPage> canisterEntry = new ArrayList<IPage>();
		canisterEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "canister.info")));
		canisterEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "canister")));
		// TODO Recipe handling
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "canister_entry"),
				new EntryItemStack(canisterEntry, keyBase + "canister", new ItemStack(BookObjectHolder.canister)));

		List<IPage> fluidHopperEntry = new ArrayList<IPage>();
		fluidHopperEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "fluid_hopper.info")));
		fluidHopperEntry.add(new PageJsonRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "fluid_hopper")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "fluid_hopper_entry"), new EntryItemStack(
				fluidHopperEntry, keyBase + "fluid_hopper", new ItemStack(BookObjectHolder.fluid_hopper)));

		List<IPage> pneumaticsEntry = new ArrayList<IPage>();
		pneumaticsEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "pneumatics.info")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "pneumatics_entry"), new EntryItemStack(
				pneumaticsEntry, keyBase + "pneumatics", new ItemStack(BookObjectHolder.pneumatic_tube)));

		List<IPage> basicTankEntry = new ArrayList<IPage>();
		basicTankEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "basic_tank.info")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "basic_tank_entry"), new EntryItemStack(
				basicTankEntry, keyBase + "basic_tank", new ItemStack(BookObjectHolder.basic_fluid_tank)));

		List<IPage> tankEntry = new ArrayList<IPage>();
		tankEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "tank.info")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "tank_entry"),
				new EntryItemStack(tankEntry, keyBase + "tank", new ItemStack(BookObjectHolder.tank_valve)));

		for(EntryAbstract entry : entries.values()) {
			PageHelper.setPagesToUnicode(entry.pageList);
		}

		return entries;
	}

}
