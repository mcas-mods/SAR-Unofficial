package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class MultiblockTankWrapper implements IFluidTank, IFluidHandler {

	final MultiblockInventoryTileEntity<?> tile;
	final String name;

	// TODO Bounds
	public MultiblockTankWrapper(MultiblockInventoryTileEntity<?> tile, String name) {
		this.tile = tile;
		this.name = name;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler().fill(resource,
					doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler().drain(maxDrain,
					doDrain);
		}
		return null;
	}

	@Override
	public FluidStack getFluid() {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler().getFluid();
		}
		return null;
	}

	@Override
	public int getFluidAmount() {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler().getFluidAmount();
		}
		return 0;
	}

	@Override
	public int getCapacity() {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler().getCapacity();
		}
		return 0;
	}

	@Override
	public FluidTankInfo getInfo() {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler().getInfo();
		}
		return null;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler().getTankProperties();
		}
		return new IFluidTankProperties[0];
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(tile.isConnected()
				&& tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler() != null) {
			return tile.getMultiblockController().getInventory().getFluidPiece(name).getHandler().drain(resource,
					doDrain);
		}
		return null;
	}

}
