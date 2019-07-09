package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.*;

/*
 * A fluid handler that holds multiple tanks in one, like an item handler has multiple slots
 */
@Deprecated // Currently cannot be used in inv system
public class FluidHandlerMulti implements IFluidHandler, INBTSerializable<NBTTagCompound> {

	FluidTankSmart[] tanks;

	public FluidHandlerMulti(InventoryBasic parent, IOType type, int... tankCapacities) {
		tanks = new FluidTankSmart[tankCapacities.length];
		for(int i = 0; i < tankCapacities.length; i++) {
			tanks[i] = new FluidTankSmart(tankCapacities[i], parent);
		}
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		FluidTankPropertiesWrapper[] infos = new FluidTankPropertiesWrapper[tanks.length];
		for(int i = 0; i < tanks.length; i++) {
			infos[i] = new FluidTankPropertiesWrapper(tanks[i]);
		}
		return infos;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		for(FluidTankSmart tank : tanks) {
			if(tank.getFluid() == null || tank.getFluid().isFluidEqual(resource)) {
				return tank.fill(resource, doFill);
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		for(FluidTankSmart tank : tanks) {
			if(tank.getFluid().isFluidEqual(resource)) {
				return tank.drain(resource, doDrain);
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		for(FluidTankSmart tank : tanks) {
			if(tank.getFluidAmount() >= maxDrain) {
				return tank.drain(maxDrain, doDrain);
			}
		}
		return null;
	}

	public int getNumberOfTanks() {
		return tanks.length;
	}

	@Deprecated
	public FluidTankSmart getTank() {
		return getTank(0);
	}

	public FluidTankSmart getTank(int number) {
		return tanks[number];
	}

	public FluidTankSmart[] getTanks() {
		return tanks;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("tanks", tanks.length);
		for(int i = 0; i < tanks.length; i++) {
			tag.setTag("tank" + i, tanks[i].serializeNBT());
		}
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		int count = nbt.getInteger("tanks");
		tanks = new FluidTankSmart[count];
		for(int i = 0; i < count; i++) {
			tanks[i].deserializeNBT(nbt.getCompoundTag("tank" + i));
		}
	}
}
