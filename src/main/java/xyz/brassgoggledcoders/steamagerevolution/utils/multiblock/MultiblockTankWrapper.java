package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;

import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class MultiblockTankWrapper implements IFluidTank, IFluidHandler {

	final MultiblockTileEntityBase<SARRectangularMultiblockControllerBase> tile;
	final String toWrap;

	public MultiblockTankWrapper(RectangularMultiblockTileEntityBase<?> tile, String toWrap) {
		this.tile = (MultiblockTileEntityBase<SARRectangularMultiblockControllerBase>) tile;
		this.toWrap = toWrap;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(tile.isConnected() && tile.getMultiblockController().getTank(toWrap) != null) {
			return tile.getMultiblockController().getTank(toWrap).fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(tile.isConnected() && tile.getMultiblockController().getTank(toWrap) != null) {
			return tile.getMultiblockController().getTank(toWrap).drain(maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack getFluid() {
		if(tile.isConnected() && tile.getMultiblockController().getTank(toWrap) != null) {
			return tile.getMultiblockController().getTank(toWrap).getFluid();
		}
		return null;
	}

	@Override
	public int getFluidAmount() {
		if(tile.isConnected() && tile.getMultiblockController().getTank(toWrap) != null) {
			return tile.getMultiblockController().getTank(toWrap).getFluidAmount();
		}
		return 0;
	}

	@Override
	public int getCapacity() {
		if(tile.isConnected() && tile.getMultiblockController().getTank(toWrap) != null) {
			return tile.getMultiblockController().getTank(toWrap).getCapacity();
		}
		return 0;
	}

	@Override
	public FluidTankInfo getInfo() {
		if(tile.isConnected() && tile.getMultiblockController().getTank(toWrap) != null) {
			return tile.getMultiblockController().getTank(toWrap).getInfo();
		}
		return null;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		if(tile.isConnected() && tile.getMultiblockController().getTank(toWrap) != null) {
			return tile.getMultiblockController().getTank(toWrap).getTankProperties();
		}
		return new IFluidTankProperties[0];
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(tile.isConnected() && tile.getMultiblockController().getTank(toWrap) != null) {
			return tile.getMultiblockController().getTank(toWrap).drain(resource, doDrain);
		}
		return null;
	}

}
