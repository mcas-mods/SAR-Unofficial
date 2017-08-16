package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import com.teamacronymcoders.base.containers.slots.SlotChanged;
import com.teamacronymcoders.base.tileentities.IOnSlotChanged;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;

public class SlotExpansion extends SlotChanged {

	@CapabilityInject(IExpansion.class)
	static Capability<IExpansion> EXPANSION_CAP = null;

	public SlotExpansion(IItemHandler itemHandler, IOnSlotChanged changeReceiver, int index, int xPos, int yPos) {
		super(itemHandler, changeReceiver, index, xPos, yPos);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return itemStack != null && itemStack.hasCapability(EXPANSION_CAP, EnumFacing.UP);
	}
}
