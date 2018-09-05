package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentity;

import com.teamacronymcoders.base.tileentities.TileEntitySidedBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.api.mechanical.IMechanicalPower;
import xyz.brassgoggledcoders.steamagerevolution.api.mechanical.ReducingMechanicalPower;

public class TileEntityGearBox extends TileEntitySidedBase<IMechanicalPower> {
    @Override
    protected void readCapability(NBTTagCompound data) {

    }

    @Override
    protected void writeCapability(NBTTagCompound data) {

    }

    @Override
    protected Capability<IMechanicalPower> getCapabilityType() {
        return SARCapabilities.MECHANICAL_POWER;
    }

    @Override
    protected ReducingMechanicalPower getInternalCapability() {
        return null;
    }

    @Override
    protected ReducingMechanicalPower getOutputCapability() {
        return null;
    }

    @Override
    protected ReducingMechanicalPower getInputCapability() {
        return null;
    }
}
