package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;

public class FluidTankSingleSmart extends FluidTankSmart {

	private String fluidName;

	public FluidTankSingleSmart(int capacity, String fluidName, IMachineHasInventory parent) {
		super(capacity, parent);
		this.fluidName = fluidName;
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return getFluidName().equals(FluidRegistry.getFluidName(fluid)) ? canFill() : false;
	}

	public String getFluidName() {
		return fluidName;
	}

}
