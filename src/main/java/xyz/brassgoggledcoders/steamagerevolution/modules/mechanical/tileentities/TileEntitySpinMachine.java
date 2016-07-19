package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.LinkedHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.boilerplate.api.IDebuggable;
import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySidedSlowTick;
import xyz.brassgoggledcoders.steamagerevolution.CapabilityHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public abstract class TileEntitySpinMachine extends TileEntitySidedSlowTick implements IDebuggable {

	protected final ISpinHandler handler;

	public TileEntitySpinMachine() {
		this.handler = new SpinHandler();
	}

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == CapabilityHandler.SPIN_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capObject, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capObject, EnumFacing side) {
		if(capObject == CapabilityHandler.SPIN_HANDLER_CAPABILITY)
			return CapabilityHandler.SPIN_HANDLER_CAPABILITY.cast(handler);

		return super.getCapability(capObject, side);
	}

	@Override
	public LinkedHashMap<String, String> getDebugStrings(LinkedHashMap<String, String> debugStrings) {
		debugStrings.put("speed", "" + this.handler.getSpeed());
		return debugStrings;
	}

	@Override
	public void readFromNBTCustom(NBTTagCompound compound) {

	}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		return compound;
	}
}
