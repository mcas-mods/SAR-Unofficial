package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.tileentities.IOnSlotChanged;
import com.teamacroynmcoders.base.containers.slots.SlotChanged;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandler;

public class SlotFluidContainer extends SlotChanged {
	public SlotFluidContainer(IItemHandler itemHandler, IOnSlotChanged changeReceiver, int index, int xPos, int yPos) {
		super(itemHandler, changeReceiver, index, xPos, yPos);
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack stack) {
		return stack != null && stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}
}