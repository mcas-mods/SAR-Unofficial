package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.teamacronymcoders.base.util.OreDictUtils;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageFurnaceRecipe;
import amerifrance.guideapi.page.PageIRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;

@ObjectHolder(SteamAgeRevolution.MODID)
public class CategoryBasics {

	public static final Item hammer = null;

	public static Map<ResourceLocation, EntryAbstract> buildCategory() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		String keyBase = "guide." + SteamAgeRevolution.MODID + ".entry.basics.";

		List<IPage> hammerEntry = new ArrayList<IPage>();
		hammerEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "hammer.info")));
		hammerEntry.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "hammer")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "hammer_entry"),
				new EntryItemStack(hammerEntry, keyBase + "hammer", new ItemStack(hammer)));

		List<IPage> oreEntry = new ArrayList<IPage>();
		oreEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "ore.info"),
				OreDictUtils.getPreferredItemStack("oreCopper")));
		// TODO Recipe handling
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "ore_entry"),
				new EntryItemStack(oreEntry, keyBase + "ore", OreDictUtils.getPreferredItemStack("oreZinc")));

		List<IPage> alloyEntry = new ArrayList<IPage>();
		alloyEntry.addAll(PageHelper.pagesForLongText(
				TextUtils.localize(keyBase + "alloy.info", OreDictUtils.getPreferredItemStack("blockBrass"))));
		alloyEntry.add(new PageFurnaceRecipe("dustBrass"));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "alloy_entry"),
				new EntryItemStack(alloyEntry, keyBase + "alloy", OreDictUtils.getPreferredItemStack("ingotBrass")));

		for(EntryAbstract entry : entries.values()) {
			PageHelper.setPagesToUnicode(entry.pageList);
		}

		return entries;
	}

}
