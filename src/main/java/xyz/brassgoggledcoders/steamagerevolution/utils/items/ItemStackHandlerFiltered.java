package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;

public abstract class ItemStackHandlerFiltered extends ItemStackHandlerSmart {

	public ItemStackHandlerFiltered(int size, IMachineHasInventory controller) {
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
		public ItemStackHandlerFuel(int size, IMachineHasInventory controller) {
			super(size, controller);
		}

		@Override
		protected boolean canInsertItem(int slot, ItemStack stack) {
			return TileEntityFurnace.isItemFuel(stack);
		}
	}

	// public static class ItemStackHandlerRecipeMachine extends
	// ItemStackHandlerFiltered {
	//
	// IMachineHasInventory controller;
	//
	// public ItemStackHandlerRecipeMachine(int size, IMachineHasInventory
	// controller) {
	// super(size, controller);
	// this.controller = controller;
	// }
	//
	// @Override
	// protected boolean canInsertItem(int slot, ItemStack stack) {
	// return
	// RecipeRegistry.getRecipesForMachine(controller.getName().toLowerCase()).parallelStream()
	// .filter(r -> Arrays.stream(r.getItemInputs()).anyMatch(ing ->
	// ing.apply(stack))).findAny()
	// .isPresent();
	// }
	// }
}
