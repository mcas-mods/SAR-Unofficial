package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankSingleType extends FluidTank {

	private String fluidName;

	public FluidTankSingleType(int capacity, String fluidName) {
		super(capacity);
		this.fluidName = fluidName;
	}

	public FluidTankSingleType(FluidStack passIn, int capacity, String fluidName) {
		super(passIn, capacity);
		this.fluidName = fluidName;
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return (FluidRegistry.getFluidName(fluid) == getFluidName()) ? canFill() : false;
	}

	public String getFluidName() {
		return fluidName;
	}

}
