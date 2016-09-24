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
	private int lastSpeed = 0;
	protected int updatesUntilSpindown;

	public TileEntitySpinMachine() {
		super();
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
	protected void readFromUpdatePacket(NBTTagCompound data) {
		this.handler.setSpeed(data.getInteger("speed"));
		super.readFromUpdatePacket(data);
	};

	@Override
	protected NBTTagCompound writeToUpdatePacket(NBTTagCompound data) {
		data.setInteger("speed", this.handler.getSpeed());
		return super.writeToUpdatePacket(data);
	};

	@Override
	public void readFromDisk(NBTTagCompound data) {
		this.handler.deserializeNBT(data.getCompoundTag("spinhandler"));
		super.readFromDisk(data);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound data) {
		data.setTag("spinhandler", this.handler.serializeNBT());
		return super.writeToDisk(data);
	};

	@Override
	public LinkedHashMap<String, String> getDebugStrings(LinkedHashMap<String, String> debugStrings) {
		debugStrings.put("speed", "" + this.handler.getSpeed());
		return debugStrings;
	}

	@Override
	public void updateTile() {
		if(this.lastSpeed != this.handler.getSpeed()) {
			onSpeedChanged(lastSpeed, this.handler.getSpeed());
		}

		// TODO
		if(this.handler.getSpeed() > 0) {
			if(this.updatesUntilSpindown > 0) {
				this.updatesUntilSpindown--;
			}
			else {
				// TODO SFX
				this.handler.setSpeed(0);
				this.onSpeedChanged(lastSpeed, 0);
			}
		}

		this.lastSpeed = this.handler.getSpeed();
		super.updateTile();
	}

	protected void onSpeedChanged(int lastSpeed, int newSpeed) {
		if(lastSpeed < newSpeed) {
			updatesUntilSpindown = 5;
		}
		this.markDirty();
		this.sendBlockUpdate();
	}
}
