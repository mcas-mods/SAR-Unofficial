package xyz.brassgoggledcoders.steamagerevolution.modules.storage.items;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class ItemCanister extends ItemBase {

	private int capacity;

	public ItemCanister(String name, int capacity) {
		super(name);
		this.setMaxStackSize(1);
		this.capacity = capacity;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FluidHandlerItemStack(stack, capacity);
	}
}
