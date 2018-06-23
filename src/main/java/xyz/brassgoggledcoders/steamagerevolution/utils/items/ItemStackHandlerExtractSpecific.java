package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class ItemStackHandlerExtractSpecific extends ItemStackHandler {

	public ItemStackHandlerExtractSpecific(int i) {
		super(i);
	}

	public void extractStack(ItemStack stack) {
		for(int i = 0; i < stacks.size(); i++) {
			if(containsItemStack(stacks.get(i), stack)) {
				extractItem(i, stack.getCount(), false);
			}
		}
	}

	public static boolean containsItemStack(ItemStack stack, ItemStack inputStack) {
		return OreDictionary.itemMatches(stack, inputStack, false) && inputStack.getCount() >= stack.getCount();
	}

}
