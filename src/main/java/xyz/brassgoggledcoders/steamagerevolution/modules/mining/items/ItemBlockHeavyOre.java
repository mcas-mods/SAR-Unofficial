package xyz.brassgoggledcoders.steamagerevolution.modules.mining.items;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.items.IHasItemColor;
import com.teamacronymcoders.base.items.itemblocks.ItemBlockModel;
import com.teamacronymcoders.base.materialsystem.materials.Material;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockHeavyOre;

public class ItemBlockHeavyOre extends ItemBlockModel<BlockHeavyOre> implements IHasItemColor {

	public ItemBlockHeavyOre(BlockHeavyOre block) {
		super(block);
	}

	@Override
	public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
		return tintIndex == 0 ? this.getActualBlock().colorMultiplier(null, null, null, tintIndex) : -1;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		Material material = this.getActualBlock().materialPart.getMaterial();
		if (I18n.canTranslate(material.getTranslationKey())) {
			String materialDisplayName = I18n.translateToLocal(material.getTranslationKey());
			return I18n.translateToLocalFormatted("steamagerevolution.part.heavy_ore", materialDisplayName);
		} else {
			return I18n.translateToLocalFormatted("steamagerevolution.part.heavy_ore", material.getName());
		}
	}
}
