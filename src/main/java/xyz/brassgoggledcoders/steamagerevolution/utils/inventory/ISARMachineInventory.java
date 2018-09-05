package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

import javax.annotation.Nullable;

public interface ISARMachineInventory {
    @Nullable
    public ItemStackHandlerExtractSpecific getInputHandler();

    @Nullable
    public MultiFluidTank getInputTank();

    @Nullable
    public ItemStackHandler getOutputHandler();

    @Nullable
    public MultiFluidTank getOutputTank();

    @Nullable
    public FluidTankSingleSmart getSteamTank();
}
