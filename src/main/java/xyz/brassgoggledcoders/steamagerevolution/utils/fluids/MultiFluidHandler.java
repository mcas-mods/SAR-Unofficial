package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IMachineHasInventory;

/*
 * A fluid handler that holds multiple tanks in one, like an item handler has multiple slots
 */
public class MultiFluidHandler implements IFluidHandler, INBTSerializable<NBTTagCompound> {

	int numberOfTanks;
	FluidTankSmart[] tanks;

	public MultiFluidHandler(int numberOfTanks, IMachineHasInventory parent, int... tankCapacities) {
		this.numberOfTanks = numberOfTanks;
		tanks = new FluidTankSmart[numberOfTanks];
		for(int i = 0; i < numberOfTanks; i++) {
			tanks[i] = new FluidTankSmart(tankCapacities[i], parent);
		}
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		FluidTankPropertiesWrapper[] infos = new FluidTankPropertiesWrapper[numberOfTanks];
		for(int i = 0; i < numberOfTanks; i++) {
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
		return numberOfTanks;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("tanks", numberOfTanks);
		for(int i = 0; i < numberOfTanks; i++) {
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