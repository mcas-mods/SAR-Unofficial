package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySlowTick;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.SpinUtils;

public class TileEntityInfiniteSpinDrain extends TileEntitySlowTick {

	private ISpinHandler handler = new SpinHandler();

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == SARAPI.SPIN_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capObject, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capObject, EnumFacing side) {
		if(capObject == SARAPI.SPIN_HANDLER_CAPABILITY) {
			return SARAPI.SPIN_HANDLER_CAPABILITY.cast(handler);
		}

		return super.getCapability(capObject, side);
	}

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		ArrayList<ISpinHandler> handlers = SpinUtils.getHandlersNearby(this.getWorld(), this.getPos());

		for(ISpinHandler handler : handlers) {
			handler.decrSpeed(handler.getSpeed());
			this.mod.getLogger().devInfo("Speed voided");
		}
	}

	@Override
	public void readFromNBTCustom(NBTTagCompound nbtTagCompound) {}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		return compound;
	}
}