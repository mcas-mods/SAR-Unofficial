package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import java.util.*;

import com.teamacronymcoders.base.materialsystem.MaterialSystem;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.entry.EntryItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;

public class CategoryAlchemical {

	public static Map<ResourceLocation, EntryAbstract> buildCategory() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		String keyBase = "guide." + SteamAgeRevolution.MODID + ".entry.alchemical.";

		List<IPage> vatsEntry = new ArrayList<IPage>();
		vatsEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "vats.info")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "vats_entry"),
				new EntryItemStack(vatsEntry, keyBase + "vats", new ItemStack(BookObjectHolder.vat_output)));

		List<IPage> acidEntry = new ArrayList<IPage>();
		acidEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "acid.info")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "acid_entry"), new EntryItemStack(acidEntry,
				keyBase + "acid", MaterialSystem.getMaterialPart("sulphur_crystal").getItemStack()));

		List<IPage> potionsEntry = new ArrayList<IPage>();
		potionsEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "potions.info")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "potions_entry"),
				new EntryItemStack(potionsEntry, keyBase + "potions", new ItemStack(BookObjectHolder.flask)));

		List<IPage> synthEntry = new ArrayList<IPage>();
		synthEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "synth.info")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "synth"),
				new EntryItemStack(synthEntry, keyBase + "synth", new ItemStack(Items.SLIME_BALL)));

		for(EntryAbstract entry : entries.values()) {
			PageHelper.setPagesToUnicode(entry.pageList);
		}

		return entries;
	}

}
