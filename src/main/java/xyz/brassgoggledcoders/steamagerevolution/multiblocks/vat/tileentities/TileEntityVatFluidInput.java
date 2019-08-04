package xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockTankWrapper;

public class TileEntityVatFluidInput extends TileEntityVatPart {
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            // TODO
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new MultiblockTankWrapper(this, "tank1"));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
        return true;
    }

}
