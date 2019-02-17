package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public abstract class ItemStackHandlerFiltered extends ItemStackHandlerSmart {

	public ItemStackHandlerFiltered(int size, MultiblockControllerBase controller) {
		super(size, controller);
	}

	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		if(!canInsertItem(slot, stack)) {
			return stack;
		}
		return super.insertItem(slot, stack, simulate);
	}

	protected abstract boolean canInsertItem(int slot, ItemStack stack);

	public static class ItemStackHandlerFuel extends ItemStackHandlerFiltered {
		public ItemStackHandlerFuel(int size, MultiblockControllerBase controller) {
			super(size, controller);
		}

		@Override
		protected boolean canInsertItem(int slot, ItemStack stack) {
			return TileEntityFurnace.isItemFuel(stack);
		}
	}

	// public static class ItemStackHandlerCrucible extends ItemStackHandlerFiltered
	// {
	//
	// public ItemStackHandlerCrucible() {
	// super(1);
	// }
	//
	// @Override
	// protected boolean canInsertItem(int slot, ItemStack stack) {
	// return CrucibleRecipe.getRecipe(stack) != null;
	// }
	//
	// }
}
