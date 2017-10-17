package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerExtractSpecific extends ItemStackHandler {

	public ItemStackHandlerExtractSpecific(int i) {
		super(i);
	}

	public void extractStack(ItemStack stack) {
		for(ItemStack internalStack : stacks) {
			if(ItemStackUtils.containsItemStack(internalStack, stack)) {
				internalStack.shrink(stack.getCount());
			}
		}
	}

}
