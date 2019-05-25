package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class MultiblockSteamWrapper implements IFluidTank, IFluidHandler {

	final SARMultiblockTileInventory<SARMultiblockInventory> tile;

	public MultiblockSteamWrapper(SARMultiblockTileInventory<?> tile) {
		this.tile = (SARMultiblockTileInventory<SARMultiblockInventory>) tile;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (tile.isConnected() && tile.getMultiblockController().getInventory().getSteamTank() != null) {
			return tile.getMultiblockController().getInventory().getSteamTank().fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (tile.isConnected() && tile.getMultiblockController().getInventory().getSteamTank() != null) {
			return tile.getMultiblockController().getInventory().getSteamTank().drain(maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack getFluid() {
		if (tile.isConnected() && tile.getMultiblockController().getInventory().getSteamTank() != null) {
			return tile.getMultiblockController().getInventory().getSteamTank().getFluid();
		}
		return null;
	}

	@Override
	public int getFluidAmount() {
		if (tile.isConnected() && tile.getMultiblockController().getInventory().getSteamTank() != null) {
			return tile.getMultiblockController().getInventory().getSteamTank().getFluidAmount();
		}
		return 0;
	}

	@Override
	public int getCapacity() {
		if (tile.isConnected() && tile.getMultiblockController().getInventory().getSteamTank() != null) {
			return tile.getMultiblockController().getInventory().getSteamTank().getCapacity();
		}
		return 0;
	}

	@Override
	public FluidTankInfo getInfo() {
		if (tile.isConnected() && tile.getMultiblockController().getInventory().getSteamTank() != null) {
			return tile.getMultiblockController().getInventory().getSteamTank().getInfo();
		}
		return null;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		if (tile.isConnected() && tile.getMultiblockController().getInventory().getSteamTank() != null) {
			return tile.getMultiblockController().getInventory().getSteamTank().getTankProperties();
		}
		return new IFluidTankProperties[0];
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (tile.isConnected() && tile.getMultiblockController().getInventory().getSteamTank() != null) {
			return tile.getMultiblockController().getInventory().getSteamTank().drain(resource, doDrain);
		}
		return null;
	}

}
