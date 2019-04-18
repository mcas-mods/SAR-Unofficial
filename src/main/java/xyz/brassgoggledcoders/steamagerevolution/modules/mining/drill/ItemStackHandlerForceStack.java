package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;

public class ItemStackHandlerForceStack extends ItemStackHandlerSmart {
	public ItemStackHandlerForceStack(int size, MultiblockControllerBase controller) {
		super(size, controller);
	}

	//Ignore the stack's stack size limit 
	@Override
	protected int getStackLimit(int slot, @Nonnull ItemStack stack)
    {
        return this.getSlotLimit(slot);
    }
}
