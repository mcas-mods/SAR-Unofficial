package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class ItemLens extends ItemBase {

	public ItemLens() {
		super("lens");
	}

	public static class LensTintHandler implements IItemColor {

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			if(tintIndex == 1) {
				return EnumDyeColor.RED.getColorValue();
			}
			return -1;
		}

	}
}
