package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.category.CategoryItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.materials.ModuleMaterials;

@Optional.Interface(iface = "amerifrance.guideapi.api.GuideAPI", modid = "guideapi", striprefs = true)
@GuideBook
public class SARGuidebook implements IGuideBook {

	@SuppressWarnings("deprecation")
	public static Book sarGuide = new Book();

	// Yes yes I know but the new way doesn't bloody work!
	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public Book buildBook() {
		sarGuide.setTitle("guide." + SteamAgeRevolution.MODID + ".title");
		sarGuide.setWelcomeMessage("guide." + SteamAgeRevolution.MODID + ".title");
		sarGuide.setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "guide"));
		sarGuide.setDisplayName("item.guide." + SteamAgeRevolution.MODID);
		sarGuide.setColor(ModuleMaterials.brassColor);
		sarGuide.setCreativeTab(SteamAgeRevolution.tab);
		sarGuide.addCategory(new CategoryItemStack(CategoryBasics.buildCategory(),
				"guide.steamagerevolution.category.basics", new ItemStack(BookObjectHolder.hammer)));
		sarGuide.addCategory(new CategoryItemStack(CategoryProduction.buildCategory(),
				"guide.steamagerevolution.category.production", new ItemStack(BookObjectHolder.steamhammer_anvil)));
		sarGuide.addCategory(new CategoryItemStack(CategoryTransportStorage.buildCategory(),
				"guide.steamagerevolution.category.transportstorage", new ItemStack(BookObjectHolder.canister)));
		sarGuide.addCategory(new CategoryItemStack(CategoryAlchemical.buildCategory(),
				"guide.steamagerevolution.category.alchemical", new ItemStack(BookObjectHolder.vat_output)));
		sarGuide.addCategory(new CategoryItemStack(CategoryUtilities.buildCategory(),
				"guide.steamagerevolution.category.utils", new ItemStack(BookObjectHolder.trunk)));
		return sarGuide;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleModel(ItemStack bookStack) {
		GuideAPI.setModel(sarGuide);
	}

	@Nullable
	@Override
	public IRecipe getRecipe(@Nonnull ItemStack bookStack) {
		return new ShapelessOreRecipe(new ResourceLocation(SteamAgeRevolution.MODID, "guide"),
				GuideAPI.getStackFromBook(sarGuide), new ItemStack(Items.BOOK), new ItemStack(Items.COAL))
						.setRegistryName("bloodmagic_guide");
	}
}