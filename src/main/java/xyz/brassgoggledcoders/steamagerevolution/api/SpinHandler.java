package xyz.brassgoggledcoders.steamagerevolution.api;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class SpinHandler implements ISpinHandler {

	public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

		public static final ResourceLocation NAME = new ResourceLocation(SteamAgeRevolution.MODID, "spin_provider");

		private final ISpinHandler cap = new SpinHandler();

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == SARAPI.SPIN_HANDLER_CAPABILITY;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(capability == SARAPI.SPIN_HANDLER_CAPABILITY) {
				return SARAPI.SPIN_HANDLER_CAPABILITY.cast(cap);
			}

			return null;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			return cap.serializeNBT();
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			cap.deserializeNBT(nbt);
		}
	}

	protected static class Factory implements Callable<ISpinHandler> {

		@Override
		public ISpinHandler call() throws Exception {
			return new SpinHandler();
		}
	}

	int storedSpin = 0;
	int maxSpin;

	public SpinHandler() {
		maxSpin = 100;
	}

	public SpinHandler(int maxSpin) {
		this.maxSpin = maxSpin;
	}

	@Override
	public int fill(EnumFacing from, int amount, boolean doFill) {
		return storedSpin += amount;
	}

	@Override
	public int drain(EnumFacing from, int amount, boolean doDrain) {
		return storedSpin -= amount;
	}

	@Override
	public boolean canFill(EnumFacing from, int amount) {
		return true;
	}

	@Override
	public boolean canDrain(EnumFacing from, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getStoredSpin() {
		return this.storedSpin;
	}

	@Override
	public int getMaxSpin() {
		return maxSpin;
	}

	@Override
	public void setStoredSpin(int toSet) {
		this.storedSpin = toSet;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("spin", storedSpin);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.storedSpin = nbt.getInteger("spin");
	}
}
