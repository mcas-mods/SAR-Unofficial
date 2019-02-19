package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import java.util.List;

import com.teamacronymcoders.base.items.IHasSubItems;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class ItemLens extends ItemBase implements IHasSubItems {

	public ItemLens() {
		super("lens");
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public List<String> getModelNames(List<String> modelNames) {
		modelNames.add(name);
		return modelNames;
	}

	@Override
	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		for(int i = 0; i < 16; ++i) {
			itemStacks.add(new ItemStack(this, 1, i));
		}
		return itemStacks;
	}

	public static class LensTintHandler implements IItemColor {

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			if(tintIndex == 1) {
				return EnumDyeColor.byMetadata(stack.getMetadata()).getColorValue();
			}
			return -1;
		}
	}
}
