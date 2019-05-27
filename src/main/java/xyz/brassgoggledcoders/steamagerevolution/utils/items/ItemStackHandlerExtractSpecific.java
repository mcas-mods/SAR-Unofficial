package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IOTYPE;

public class ItemStackHandlerExtractSpecific extends ItemStackHandler {

	IOTYPE type = IOTYPE.UNDEFINED;

	public ItemStackHandlerExtractSpecific(int i) {
		super(i);
	}

	public static boolean containsItemStack(ItemStack stack, ItemStack inputStack) {
		return OreDictionary.itemMatches(stack, inputStack, false) && inputStack.getCount() >= stack.getCount();
	}

	public boolean extractStack(ItemStack stack) {
		for(int i = 0; i < stacks.size(); i++) {
			if(containsItemStack(stacks.get(i), stack)) {
				if(extractItem(i, stack.getCount(), true).isEmpty()) {
					extractItem(i, stack.getCount(), false);
					return true;
				}
			}
		}
		return false;
	}

	public void setType(IOTYPE input) {
		this.type = input;
	}

}
