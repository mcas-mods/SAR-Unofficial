package xyz.brassgoggledcoders.steamagerevolution;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageFurnaceRecipe;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageText;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.materials.ModuleMaterials;

@GuideBook
@ObjectHolder(SteamAgeRevolution.MODID)
public class SARGuidebook implements IGuideBook {

	public static final Item hammer = null;

	public static Book sarGuide;

	@Nonnull
	@Override
	public Book buildBook() {
		// Create the map of entries. A LinkedHashMap is used to retain the order of entries.
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();

		// Creation of our first entry.
		List<IPage> pages1 = new ArrayList<IPage>();
		pages1.add(new PageText("This is a page in my guide!"));
		pages1.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "recipes/hammer.json")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "entry_one"),
				new EntryItemStack(pages1, "The Hammer", new ItemStack(hammer)));

		// Creation of our second entry.
		List<IPage> pages2 = new ArrayList<IPage>();
		// pages2.add(new PageIRecipe(new ShapedOreRecipe(Items.DIAMOND_SWORD, "D", "D", "S", 'D', Items.DIAMOND, 'S',
		// Items.STICK)));
		pages2.add(new PageFurnaceRecipe("oreGold"));
		entries.put(new ResourceLocation("example", "entry_two"),
				new EntryItemStack(pages2, "Entry Two", new ItemStack(Items.DIAMOND_SWORD)));

		// Setup the list of categories and add our entries to it.
		List<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();
		categories.add(new CategoryItemStack(entries, "Basics", new ItemStack(hammer)));

		// Setup the book's base information
		sarGuide = new Book();
		sarGuide.setTitle(SteamAgeRevolution.MODNAME + " Guide");
		sarGuide.setDisplayName(SteamAgeRevolution.MODNAME + " Guide");
		sarGuide.setAuthor("warlordjones");
		sarGuide.setColor(ModuleMaterials.brassColor);
		sarGuide.setCategoryList(categories);
		sarGuide.setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "guidebook"));
		sarGuide.setCreativeTab(SteamAgeRevolution.tab);
		return sarGuide;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleModel(ItemStack bookStack) {
		// Use the default GuideAPI model
		GuideAPI.setModel(sarGuide);
	}

	@Override
	public void handlePost(ItemStack bookStack) {
		// TODO
		GameRegistry.addShapelessRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "guidebook"),
				new ResourceLocation(SteamAgeRevolution.MODID, "other"), bookStack,
				new Ingredient[] {Ingredient.fromItem(Items.BOOK), Ingredient.fromItem(Items.COAL)});
	}
}