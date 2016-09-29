package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.Arrays;
import java.util.LinkedHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.boilerplate.api.IDebuggable;
import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySidedSlowTick;
import xyz.brassgoggledcoders.boilerplate.utils.PositionUtils;
import xyz.brassgoggledcoders.steamagerevolution.CapabilityHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public abstract class TileEntitySpinMachine extends TileEntitySidedSlowTick implements IDebuggable {

	protected final ISpinHandler handler;
	private int lastSpeed = 0;
	protected int[] nearbyHandlerCache;

	public TileEntitySpinMachine() {
		super();
		nearbyHandlerCache = new int[6];
		Arrays.fill(nearbyHandlerCache, 0);
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
		if(data.getIntArray("cache").length > 0)
			this.nearbyHandlerCache = data.getIntArray("cache");
		super.readFromDisk(data);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound data) {
		data.setTag("spinhandler", this.handler.serializeNBT());
		data.setIntArray("cache", nearbyHandlerCache);
		return super.writeToDisk(data);
	};

	@Override
	public LinkedHashMap<String, String> getDebugStrings(LinkedHashMap<String, String> debugStrings) {
		debugStrings.put("speed", "" + this.handler.getSpeed());
		debugStrings.put("nearbyHandlerCache", Arrays.toString(nearbyHandlerCache));
		return debugStrings;
	}

	@Override
	public void updateTile() {
		if(this.lastSpeed != this.handler.getSpeed()) {
			onSpeedChanged(lastSpeed, this.handler.getSpeed());
		}
		this.lastSpeed = this.handler.getSpeed();
		super.updateTile();
	}

	protected void onSpeedChanged(int lastSpeed, int newSpeed) {
		// Cascade
		for(int i = 0; i < nearbyHandlerCache.length; i++) {
			if(nearbyHandlerCache[i] == 0)
				return;
			EnumFacing facing = EnumFacing.VALUES[i];
			ISpinHandler handler = this.getWorld().getTileEntity(this.getPos().offset(facing))
					.getCapability(CapabilityHandler.SPIN_HANDLER_CAPABILITY, facing);
			if(handler.getSpeed() != newSpeed)
				handler.setSpeed(newSpeed);
		}
		this.markDirty();
		this.sendBlockUpdate();
	}

	public void onNeighbourChange(BlockPos neighbor) {
		int facingIndex = PositionUtils.getFacingFromPositions(this.getPos(), neighbor).getIndex();
		if(this.getWorld().getTileEntity(neighbor).hasCapability(CapabilityHandler.SPIN_HANDLER_CAPABILITY, null)) {
			this.nearbyHandlerCache[facingIndex] = 1;
		}
		else
			this.nearbyHandlerCache[facingIndex] = 0;
	}
}
