package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public interface ISARMachine {
	public ItemStackHandlerExtractSpecific getItemInput();

	public MultiFluidTank getFluidInputs();

	public ItemStackHandler getItemOutput();

	public MultiFluidTank getFluidOutputs();
}
