package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;

public class FluidTankSingleSmart extends FluidTankSmart {

	private String fluidName;

	public FluidTankSingleSmart(int capacity, String fluidName, IHasInventory parent, IOType type) {
		super(capacity, parent, type);
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
