package xyz.brassgoggledcoders.steamagerevolution.modules.armory.expandablearmor;

import com.teamacronymcoders.base.containers.slots.SlotChanged;
import com.teamacronymcoders.base.tileentities.IOnSlotChanged;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;

public class SlotExpansionContainer extends SlotChanged {

	@CapabilityInject(IExpansionContainer.class)
	static Capability<IExpansionContainer> EXPANSION_CONTAINER_CAP = null;

	public SlotExpansionContainer(IItemHandler itemHandler, IOnSlotChanged changeReceiver, int slotIndex, int posX,
			int posY) {
		super(itemHandler, changeReceiver, slotIndex, posX, posY);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return itemStack != null && itemStack.hasCapability(EXPANSION_CONTAINER_CAP, EnumFacing.UP);
	}
}
