package xyz.brassgoggledcoders.steamagerevolution.modules.storage.items;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.teamacronymcoders.base.items.IHasSubItems;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.ModuleStorage;

public class ItemCanister extends ItemBase implements IHasSubItems {

	private int capacity;

	public ItemCanister(String name, int capacity) {
		super(name);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.capacity = capacity;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		FluidStack fluid = ((FluidHandlerItemStack) stack
				.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)).getFluid();
		if(fluid == null) {
			tooltip.add("0mB/" + capacity + "mB");
		}
		else {
			tooltip.add(StringUtils.capitalize(FluidRegistry.getFluidName(fluid)));
			tooltip.add(fluid.amount + "mB/" + capacity + "mB");
		}
	}

	@Override
	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
			ItemStack filledStack = new ItemStack(ModuleStorage.canister);
			filledStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
					.fill(new FluidStack(fluid, capacity), true);
			itemStacks.add(filledStack);
		}
		return itemStacks;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FluidHandlerItemStack(stack, capacity);
	}
}
