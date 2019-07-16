package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.TileEntityInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class TileEntityCastingBench extends TileEntityInventory<InventoryCraftingMachine> {

	public static int inputCapacity = RecipeUtil.VALUE_BLOCK;

	public TileEntityCastingBench() {
		setInventory(new InventoryCraftingMachine(this).addFluidHandler("tank", IOType.INPUT, 51, 11, inputCapacity)
				.addItemHandler("output", IOType.OUTPUT, 109, 34).setProgressBar(80, 34));
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
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inventory.getFluidPiece("tank").getHandler());
		}
		else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory.getItemPiece("output").getHandler());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getName() {
		return "Casting Bench";
	}
}
