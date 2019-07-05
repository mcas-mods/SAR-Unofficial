package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidHandlerMulti;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class TileEntityCastingBench extends SARMachineTileEntity {

	public static int inputCapacity = RecipeUtil.VALUE_BLOCK;

	public TileEntityCastingBench() {
		setInventory(new InventoryBasic()
				.setFluidInput(51, 11, new FluidHandlerMulti(this, IOType.INPUT, inputCapacity))
				.setItemOutput(new int[] { 109 }, new int[] { 34 }, new ItemStackHandlerExtractSpecific(1)));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(facing != null && facing.getAxis() == Axis.Y) {
			return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
					|| super.hasCapability(capability, facing);
		}
		else {
			return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
					|| super.hasCapability(capability, facing);
		}
	}

	// TODO Automatic siding
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inventory.getInputFluidHandler());
		}
		else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory.getOutputItemHandler());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getName() {
		return "Casting Bench";
	}
}
