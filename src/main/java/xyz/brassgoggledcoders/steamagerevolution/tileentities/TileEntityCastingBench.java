package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.TileEntityCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class TileEntityCastingBench extends TileEntityCraftingMachine<InventoryCraftingMachine> {

	public static final String uid = "casting_bench";
	public static int inputCapacity = RecipeUtil.VALUE_BLOCK;

	static {
		IMachine.referenceMachinesList.put(uid, new TileEntityCastingBench());
	}

	public TileEntityCastingBench() {
		setInventory(new InventoryBuilder<>(new InventoryCraftingMachine(this))
				.addPiece("tank", new InventoryPieceFluidTank(IOType.INPUT, inputCapacity, 51, 31))
				.addPiece("output", new InventoryPieceItemHandler(IOType.OUTPUT, 109, 34)).setProgressBar(80, 34)
				.build());
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
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
					.cast(inventory.getHandler("tank", FluidTankSync.class));
		}
		else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
					.cast(inventory.getHandler("output", ItemStackHandlerSync.class));
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public ItemStack getCatalyst() {
		return new ItemStack(SARObjectHolder.casting_bench);
	}
}
