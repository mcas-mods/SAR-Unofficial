package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import javax.annotation.Nonnull;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.page.PageJsonRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.materials.ModuleMaterials;

@GuideBook
@ObjectHolder(SteamAgeRevolution.MODID)
public class SARGuidebook implements IGuideBook {

	public static final Item hammer = null;
	public static final Item canister = null;
	public static final Block steamhammer_anvil = null;
	public static final Block trunk = null;

	public static Book sarGuide;

	@Nonnull
	@Override
	public Book buildBook() {
		// Setup the book's base information
		sarGuide = new Book();
		sarGuide.setTitle("guide." + SteamAgeRevolution.MODID + ".title");
		sarGuide.setDisplayName("guide." + SteamAgeRevolution.MODID + ".name");
		sarGuide.setWelcomeMessage("guide." + SteamAgeRevolution.MODID + ".welcome");
		sarGuide.setAuthor("warlordjones");
		sarGuide.setColor(ModuleMaterials.brassColor);
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
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			sarGuide.addCategory(new CategoryItemStack(CategoryBasics.buildCategory(),
					"guide.steamagerevolution.category.basics", new ItemStack(hammer)));
			sarGuide.addCategory(new CategoryItemStack(CategoryProduction.buildCategory(),
					"guide.steamagerevolution.category.production", new ItemStack(steamhammer_anvil)));
			sarGuide.addCategory(new CategoryItemStack(CategoryTransportStorage.buildCategory(),
					"guide.steamagerevolution.category.transportstorage", new ItemStack(canister)));
			sarGuide.addCategory(new CategoryItemStack(CategoryUtilities.buildCategory(),
					"guide.steamagerevolution.category.utils", new ItemStack(trunk)));
		}
		// TODO
		GameRegistry.addShapelessRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "guidebook"),
				new ResourceLocation(SteamAgeRevolution.MODID, "other"), bookStack,
				new Ingredient[] {Ingredient.fromItem(Items.BOOK), Ingredient.fromItem(Items.COAL)});
		// TODO Copied from GuideAPI since their implementation does not appear to work.
		for(CategoryAbstract cat : sarGuide.getCategoryList()) {
			for(EntryAbstract entry : cat.entries.values()) {
				for(IPage page : entry.pageList) {
					if(page instanceof PageJsonRecipe) {
						((PageJsonRecipe) page).init();
					}
				}
			}
		}
	}

}