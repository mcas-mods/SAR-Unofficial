package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class MultiblockTankWrapper implements IFluidTank, IFluidHandler {

	final SARMultiblockTileInventory<SARMultiblockInventory> tile;
	// TODO Also use this boolean to prevent insertion into outputs (still allow
	// extraction from outputs
	final boolean output;

	public MultiblockTankWrapper(SARMultiblockTileInventory<?> tile, boolean output) {
		this.tile = (SARMultiblockTileInventory<SARMultiblockInventory>) tile;
		this.output = output;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().getFluidHandler(output) != null) {
			return tile.getMultiblockController().getInventory().getFluidHandler(output).fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().getFluidHandler(output) != null) {
			return tile.getMultiblockController().getInventory().getFluidHandler(output).drain(maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack getFluid() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().getFluidHandler(output) != null) {
			return tile.getMultiblockController().getInventory().getFluidHandler(output).getFluid();
		}
		return null;
	}

	@Override
	public int getFluidAmount() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().getFluidHandler(output) != null) {
			return tile.getMultiblockController().getInventory().getFluidHandler(output).getFluidAmount();
		}
		return 0;
	}

	@Override
	public int getCapacity() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().getFluidHandler(output) != null) {
			return tile.getMultiblockController().getInventory().getFluidHandler(output).getCapacity();
		}
		return 0;
	}

	@Override
	public FluidTankInfo getInfo() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().getFluidHandler(output) != null) {
			return tile.getMultiblockController().getInventory().getFluidHandler(output).getInfo();
		}
		return null;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().getFluidHandler(output) != null) {
			return tile.getMultiblockController().getInventory().getFluidHandler(output).getTankProperties();
		}
		return new IFluidTankProperties[0];
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(tile.isConnected() && tile.getMultiblockController().getInventory().getFluidHandler(output) != null) {
			return tile.getMultiblockController().getInventory().getFluidHandler(output).drain(resource, doDrain);
		}
		return null;
	}

}
