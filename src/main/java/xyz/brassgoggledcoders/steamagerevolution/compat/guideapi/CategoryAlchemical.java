package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import java.util.LinkedHashMap;
import java.util.Map;

import com.teamacronymcoders.base.materialsystem.MaterialSystem;

import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class CategoryAlchemical {

	public static Map<ResourceLocation, EntryAbstract> buildCategory() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		String keyBase = "guide." + SteamAgeRevolution.MODID + ".entry.alchemical.";

		BookUtils.addBasicEntry(entries, keyBase, "vat", new ItemStack(BookObjectHolder.vat_output));

		BookUtils.addBasicEntry(entries, keyBase, "acid", MaterialSystem.getMaterialPart("sulphur_crystal").getItemStack());

		BookUtils.addBasicEntry(entries, keyBase, "potions", new ItemStack(BookObjectHolder.flask));

		BookUtils.addBasicEntry(entries, keyBase, "synth", new ItemStack(Items.SLIME_BALL));

		for(EntryAbstract entry : entries.values()) {
			PageHelper.setPagesToUnicode(entry.pageList);
		}

		return entries;
	}

}
