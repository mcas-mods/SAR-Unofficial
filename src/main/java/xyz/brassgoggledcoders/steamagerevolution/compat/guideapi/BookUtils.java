package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageImage;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class BookUtils {

	public static void addBasicEntry(Map<ResourceLocation, EntryAbstract> entries, String keyBase, String key,
			ItemStack displayStack, boolean img) {
		List<IPage> entry = new ArrayList<IPage>();
		if(img) {
			entry.add(new PageImage(new ResourceLocation(SteamAgeRevolution.MODID, "textures/images/" + key + ".png")));
		}
		entry.addAll(PageHelper.pagesForLongText(I18n.translateToLocalFormatted(keyBase + key + ".info")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, key + "_entry"),
				new EntryItemStack(entry, keyBase + key, displayStack));
	}
}
