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
		this.setHasSubtypes(true);
		this.capacity = capacity;
	}

	// @SideOnly(Side.CLIENT)
	// @Override
	// public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
	// // empty
	// subItems.add(new ItemStack(item));
	// for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
	// ItemStack filledStack = new ItemStack(item);
	// filledStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)
	// .fill(new FluidStack(fluid, capacity), true);
	// subItems.add(filledStack);
	// }
	// }

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FluidHandlerItemStack(stack, capacity);
	}
}
