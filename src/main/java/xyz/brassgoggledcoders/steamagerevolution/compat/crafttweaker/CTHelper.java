package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CTHelper {
	public static FluidStack toFluidStack(ILiquidStack iStack) {
		if(iStack == null) {
			return null;
		}
		return (FluidStack) iStack.getInternal();
	}

	public static ItemStack toItemStack(IItemStack iStack) {
		if(iStack == null) {
			return ItemStack.EMPTY;
		}
		return (ItemStack) iStack.getInternal();
	}
}
