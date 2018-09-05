package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class MultiblockInventoryWrapper implements IItemHandler, IItemHandlerModifiable {

    final SARMultiblockTileInventory<SARMultiblockInventory> tile;
    // TODO Also use this boolean to prevent insertion into outputs (still allow
    // extraction from outputs
    final boolean output;

    public MultiblockInventoryWrapper(SARMultiblockTileInventory<?> tile, boolean output) {
        this.tile = (SARMultiblockTileInventory<SARMultiblockInventory>) tile;
        this.output = output;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        if (tile.isConnected() && tile.getMultiblockController().getInventory().getItemHandler(output) != null) {
            tile.getMultiblockController().getInventory().getItemHandler(output).setStackInSlot(slot, stack);
        }
    }

    @Override
    public int getSlots() {
        if (tile.isConnected() && tile.getMultiblockController().getInventory().getItemHandler(output) != null) {
            return tile.getMultiblockController().getInventory().getItemHandler(output).getSlots();
        }
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (tile.isConnected() && tile.getMultiblockController().getInventory().getItemHandler(output) != null) {
            return tile.getMultiblockController().getInventory().getItemHandler(output).getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (tile.isConnected() && tile.getMultiblockController().getInventory().getItemHandler(output) != null) {
            return tile.getMultiblockController().getInventory().getItemHandler(output).insertItem(slot, stack,
                    simulate);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (tile.isConnected() && tile.getMultiblockController().getInventory().getItemHandler(output) != null) {
            return tile.getMultiblockController().getInventory().getItemHandler(output).extractItem(slot, amount,
                    simulate);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        if (tile.isConnected() && tile.getMultiblockController().getInventory().getItemHandler(output) != null) {
            return tile.getMultiblockController().getInventory().getItemHandler(output).getSlotLimit(slot);
        }
        return 0;
    }

}
