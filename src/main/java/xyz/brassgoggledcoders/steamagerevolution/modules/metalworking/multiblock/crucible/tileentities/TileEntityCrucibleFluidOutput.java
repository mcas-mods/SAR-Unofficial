package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.ContainerSingleTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiSingleTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.MultiblockTankWrapper;

public class TileEntityCrucibleFluidOutput extends TileEntityCruciblePart implements IHasGui {

    public TileEntityCrucibleFluidOutput() {

    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new MultiblockTankWrapper(this, true));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
        return false;
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {

        return true;
    }

    @Override
    public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {

        return true;
    }

    @Override
    public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {

        return false;
    }

    @Override
    public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        return new GuiSingleTank(entityPlayer, this);
    }

    @Override
    public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        // TODO Auto-generated method stub
        return new ContainerSingleTank(entityPlayer, this);
    }

}
