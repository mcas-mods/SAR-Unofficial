package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;

public class MultiblockSteamWrapper implements IFluidTank, IFluidHandler {

	final MultiblockInventoryTileEntity<? extends MultiblockCraftingMachine<?>> tile;

	// TODO Why can't the second type be bounded like on inventory wrapper?
	public MultiblockSteamWrapper(MultiblockInventoryTileEntity<? extends MultiblockCraftingMachine<?>> tile) {
		this.tile = tile;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().steamPiece.getHandler() != null) {
			return tile.getMultiblockController().getInventory().steamPiece.getHandler().fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().steamPiece.getHandler() != null) {
			return tile.getMultiblockController().getInventory().steamPiece.getHandler().drain(maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack getFluid() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().steamPiece.getHandler() != null) {
			return tile.getMultiblockController().getInventory().steamPiece.getHandler().getFluid();
		}
		return null;
	}

	@Override
	public int getFluidAmount() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().steamPiece.getHandler() != null) {
			return tile.getMultiblockController().getInventory().steamPiece.getHandler().getFluidAmount();
		}
		return 0;
	}

	@Override
	public int getCapacity() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().steamPiece.getHandler() != null) {
			return tile.getMultiblockController().getInventory().steamPiece.getHandler().getCapacity();
		}
		return 0;
	}

	@Override
	public FluidTankInfo getInfo() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().steamPiece.getHandler() != null) {
			return tile.getMultiblockController().getInventory().steamPiece.getHandler().getInfo();
		}
		return null;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().steamPiece.getHandler() != null) {
			return tile.getMultiblockController().getInventory().steamPiece.getHandler().getTankProperties();
		}
		return new IFluidTankProperties[0];
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().steamPiece.getHandler() != null) {
			return tile.getMultiblockController().getInventory().steamPiece.getHandler().drain(resource, doDrain);
		}
		return null;
	}

}
