package xyz.brassgoggledcoders.steamagerevolution.utils;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankSingleSmart extends FluidTankSmart {

	private String fluidName;

	public FluidTankSingleSmart(int capacity, String fluidName, TileEntity parent) {
		super(capacity, parent);
		this.fluidName = fluidName;
	}

	public FluidTankSingleSmart(int capacity, String fluidName, MultiblockControllerBase parent) {
		super(capacity, parent);
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
