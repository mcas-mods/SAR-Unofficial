package xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.IItemHandler;
import xyz.brassgoggledcoders.boilerplate.containers.slots.SlotChanged;
import xyz.brassgoggledcoders.boilerplate.tileentities.IOnSlotChanged;

public class SlotFuel extends SlotChanged {

	public SlotFuel(IItemHandler itemHandler, IOnSlotChanged changeReceiver, int slotIndex, int posX, int posY) {
		super(itemHandler, changeReceiver, slotIndex, posX, posY);
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack stack) {
		return TileEntityFurnace.isItemFuel(stack);
	}

}
