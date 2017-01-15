package xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.tileentities.IOnSlotChanged;
import com.teamacroynmcoders.base.containers.slots.SlotChanged;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.IItemHandler;

public class SlotFuel extends SlotChanged {

	public SlotFuel(IItemHandler itemHandler, IOnSlotChanged changeReceiver, int slotIndex, int posX, int posY) {
		super(itemHandler, changeReceiver, slotIndex, posX, posY);
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack stack) {
		return TileEntityFurnace.isItemFuel(stack);
	}

}
