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
	public void onContentsChanged(int slot)
    {
		for(int i = 0; i < this.getSlots(); i++) {
			for(int i2 = 0; i < this.getSlots(); i2++)
			if(this.getStackInSlot(i).getCount() < this.getSlotLimit(i) && ItemStack.areItemsEqual(this.getStackInSlot(i), this.getStackInSlot(i2))) {
				this.setStackInSlot(i, this.getStackInSlot(i2));
				this.getStackInSlot(i2).setCount(0);
			}
		}
    }
}
