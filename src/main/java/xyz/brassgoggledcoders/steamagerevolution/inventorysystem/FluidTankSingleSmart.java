package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankSingleSmart extends FluidTankSmart {

	private String fluidName;

	public FluidTankSingleSmart(int capacity, String fluidName, InventoryBasic parent) {
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
