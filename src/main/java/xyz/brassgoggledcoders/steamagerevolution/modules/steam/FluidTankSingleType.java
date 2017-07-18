package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankSingleType extends FluidTankUpdateSensitive {

	private String fluidName;

	public FluidTankSingleType(int capacity, String fluidName) {
		this(capacity, fluidName, null);
	}

	public FluidTankSingleType(int capacity, String fluidName, TileEntity parent) {
		super(null, capacity, parent);
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
