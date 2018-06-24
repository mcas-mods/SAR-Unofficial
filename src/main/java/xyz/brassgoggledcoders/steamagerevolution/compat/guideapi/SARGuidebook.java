package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import javax.annotation.Nonnull;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.category.CategoryItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.materials.ModuleMaterials;

@GuideBook
public class SARGuidebook implements IGuideBook {

	public static Book sarGuide;

	@Nonnull
	@Override
	public Book buildBook() {
		return new BookBinder(new ResourceLocation(SteamAgeRevolution.MODID, "guide"))
				.setGuideTitle("guide." + SteamAgeRevolution.MODID + ".title").setColor(ModuleMaterials.brassColor)
				.setCreativeTab(SteamAgeRevolution.tab)
				.addCategory(new CategoryItemStack(CategoryBasics.buildCategory(),
						"guide.steamagerevolution.category.basics", new ItemStack(BookObjectHolder.hammer)))
				.addCategory(new CategoryItemStack(CategoryProduction.buildCategory(),
						"guide.steamagerevolution.category.production",
						new ItemStack(BookObjectHolder.steamhammer_anvil)))
				.addCategory(new CategoryItemStack(CategoryTransportStorage.buildCategory(),
						"guide.steamagerevolution.category.transportstorage", new ItemStack(BookObjectHolder.canister)))
				.addCategory(new CategoryItemStack(CategoryAlchemical.buildCategory(),
						"guide.steamagerevolution.category.alchemical", new ItemStack(BookObjectHolder.vat_output)))
				.addCategory(new CategoryItemStack(CategoryUtilities.buildCategory(),
						"guide.steamagerevolution.category.utils", new ItemStack(BookObjectHolder.trunk)))
				.build();
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
				new Ingredient[] { Ingredient.fromItem(Items.BOOK), Ingredient.fromItem(Items.COAL) });
	}

}