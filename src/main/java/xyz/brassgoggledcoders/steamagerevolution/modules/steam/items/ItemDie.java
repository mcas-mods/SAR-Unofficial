package xyz.brassgoggledcoders.steamagerevolution.modules.steam.items;

import java.util.Arrays;
import java.util.List;

import com.teamacronymcoders.base.items.IHasSubItems;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.item.ItemStack;

public class ItemDie extends ItemBase implements IHasSubItems {

	public static String[] dieShapes = new String[] {"Gear", "Test"};

	public ItemDie() {
		super("die");
		this.setMaxStackSize(1);
	}

	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		for(int i = 0; i < dieShapes.length; i++) {
			itemStacks.add(new ItemStack(this.getItem(), 1, i));
		}
		return itemStacks;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "." + dieShapes[stack.getItemDamage()].toLowerCase();
	}

	public static String getDieNameFromMeta(ItemStack stack) {
		return dieShapes[stack.getItemDamage()];
	}

	public static int getMetaFromDieName(String name) {
		return Arrays.asList(dieShapes).indexOf(name);
	}

}
