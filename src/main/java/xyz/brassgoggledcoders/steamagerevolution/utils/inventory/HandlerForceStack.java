package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class HandlerForceStack extends ItemStackHandlerExtractSpecific {
	public HandlerForceStack(int slots) {
		super(slots);
	}

	@Override
	protected int getStackLimit(int slot, @Nonnull ItemStack stack)
    {
		//TODO
		if(stack.hasTagCompound() || stack.getItem().getMaxDamage(stack) > 0) {
			return 1;
		}
        return getSlotLimit(slot);
    }
	
	//TODO Bluuuuurgh
	@Override
	public void setStackInSlot(int from, @Nonnull ItemStack stack)
    {
		for(int to = 0; to < this.getSlots(); to++) {
			if(this.getStackInSlot(to).isItemEqual(stack)) {
				int count = this.getStackInSlot(to).getCount();
				this.stacks.set(to, new ItemStack(stack.getItem(), stack.getCount() + count, stack.getMetadata()));
				return;
			}
		}
		super.setStackInSlot(from, stack);
    }
}
