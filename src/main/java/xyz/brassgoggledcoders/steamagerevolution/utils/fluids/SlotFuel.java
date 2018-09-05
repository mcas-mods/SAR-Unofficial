package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import com.teamacronymcoders.base.containers.slots.SlotChanged;
import com.teamacronymcoders.base.tileentities.IOnSlotChanged;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class SlotFuel extends SlotChanged {

    public SlotFuel(IItemHandler itemHandler, IOnSlotChanged changeReceiver, int slotIndex, int posX, int posY) {
        super(itemHandler, changeReceiver, slotIndex, posX, posY);
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return TileEntityFurnace.isItemFuel(stack);
    }

}
