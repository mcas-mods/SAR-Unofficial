package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankSingleSmart extends FluidTankSmart {

	private String fluidName;

	@Deprecated
	public FluidTankSingleSmart(int capacity, String fluidName, ISmartTankCallback parent) {
		this(capacity, fluidName, parent, -1);
	}

	public FluidTankSingleSmart(int capacity, String fluidName, ISmartTankCallback parent, int id) {
		super(capacity, parent, id);
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
