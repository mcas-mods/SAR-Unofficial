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
		//return 64;
        return getSlotLimit(slot);
    }
	
	@Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        validateSlotIndex(slot);
        ItemStack existing = this.stacks.get(slot);

        if(existing.getMaxStackSize() < this.getSlotLimit(slot)) {
        	amount = existing.getMaxStackSize();
        }
        return super.extractItem(slot, amount, simulate);
    }
}
