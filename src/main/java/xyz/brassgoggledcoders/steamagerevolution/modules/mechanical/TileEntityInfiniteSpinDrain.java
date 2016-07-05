package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySlowTick;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public class TileEntityInfiniteSpinDrain extends TileEntitySlowTick {
	private ISpinHandler handler = new SpinHandler(1000000000);
	// TODO Move rate to ISpinHandler
	private int spinPer = 10;

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

		this.handler.drain(this.handler.getMaxSpin());

		// TODO Move to ISpinHandler as 'pushPower' method
		for(int i = 0; i < EnumFacing.VALUES.length; i++) {
			if(this.getWorld().getTileEntity(getPos().offset(EnumFacing.VALUES[i]))
					.hasCapability(SARAPI.SPIN_HANDLER_CAPABILITY, EnumFacing.VALUES[i].getOpposite())) {
				this.getWorld().getTileEntity(getPos().offset(EnumFacing.VALUES[i]))
						.getCapability(SARAPI.SPIN_HANDLER_CAPABILITY, EnumFacing.VALUES[i].getOpposite())
						.drain(this.spinPer);
			}
		}
	}

	@Override
	public void readFromNBTCustom(NBTTagCompound nbtTagCompound) {}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		return compound;
	}
}