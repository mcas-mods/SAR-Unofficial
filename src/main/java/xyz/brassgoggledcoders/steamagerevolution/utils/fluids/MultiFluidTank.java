package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;

/**
 * @author BluSunrize - 20.02.2017
 */
//TODO The use of this in the Inv system needs rewriting. A multifluid tank for one fluid type is dumb. 
@Deprecated
public class MultiFluidTank extends FluidTankSmart {
	private final int capacity;
	private final int maxFluids;
	public ArrayList<FluidStack> fluids;

	public MultiFluidTank(int capacity, IMachineHasInventory parent, int maxFluids) {
		super(capacity, parent);
		this.capacity = capacity;
		this.maxFluids = maxFluids;
		this.fluids = new ArrayList<>(maxFluids);
	}

	public static FluidStack copyFluidStackWithAmount(FluidStack stack, int amount) {
		if (stack == null) {
			return null;
		}
		FluidStack fs = new FluidStack(stack, amount);
		return fs;
	}

	public static FluidStack drain(int remove, FluidStack removeFrom, Iterator<FluidStack> removeIt, boolean doDrain) {
		int amount = Math.min(remove, removeFrom.amount);
		if (doDrain) {
			removeFrom.amount -= amount;
			if (removeFrom.amount <= 0) {
				removeIt.remove();
			}
		}
		return copyFluidStackWithAmount(removeFrom, amount);
	}

	@Override
	public MultiFluidTank readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("fluids")) {
			fluids.clear();
			NBTTagList tagList = nbt.getTagList("fluids", 10);
			for (int i = 0; i < tagList.tagCount(); i++) {
				FluidStack fs = FluidStack.loadFluidStackFromNBT(tagList.getCompoundTagAt(i));
				if (fs != null) {
					fluids.add(fs);
				}
			}
		}
		return this;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList tagList = new NBTTagList();
		for (FluidStack fs : fluids) {
			if (fs != null) {
				tagList.appendTag(fs.writeToNBT(new NBTTagCompound()));
			}
		}
		nbt.setTag("fluids", tagList);
		return nbt;
	}

	public int getFluidTypes() {
		return fluids.size();
	}

	@Nullable
	@Override
	public FluidStack getFluid() {
		// grabbing the last fluid, for output reasons
		return fluids.size() > 0 ? fluids.get(fluids.size() - 1) : null;
	}

	@Override
	public int getFluidAmount() {
		int sum = 0;
		for (FluidStack fs : fluids) {
			sum += fs.amount;
		}
		return sum;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public FluidTankInfo getInfo() {
		FluidStack fs = getFluid();
		int capacity = this.capacity - getFluidAmount();
		if (fs != null) {
			capacity += fs.amount;
		}
		return new FluidTankInfo(fs, capacity);
	}

	// FIXME
	@Override
	public IFluidTankProperties[] getTankProperties() {
		if (tankProperties == null) {
			tankProperties = new IFluidTankProperties[] { new FluidTankPropertiesWrapper(this) };
		}
		return tankProperties;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		//May not insert more fluid types past the fluid limit
		if(this.fluids.size() == maxFluids)  {
			return 0;
		}
		int space = capacity - getFluidAmount();
		int toFill = Math.min(resource.amount, space);
		if (!doFill) {
			return toFill;
		}
		for (FluidStack fs : fluids) {
			if (fs.isFluidEqual(resource)) {
				fs.amount += toFill;
				onContentsChanged();
				return toFill;
			}
		}
		fluids.add(copyFluidStackWithAmount(resource, toFill));
		return toFill;

	}

	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (fluids.isEmpty()) {
			return null;
		}
		Iterator<FluidStack> it = fluids.iterator();
		while (it.hasNext()) {
			FluidStack fs = it.next();
			if (fs.isFluidEqual(resource)) {
				int amount = Math.min(resource.amount, fs.amount);
				if (doDrain) {
					onContentsChanged();
					fs.amount -= amount;
					if (fs.amount <= 0) {
						it.remove();
					}
				}
				return copyFluidStackWithAmount(resource, amount);
			}
		}
		return null;
	}

	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (fluids.isEmpty()) {
			return null;
		}
		return drain(new FluidStack(getFluid(), maxDrain), doDrain);
	}

	public int getMaxFluids() {
		return maxFluids;
	}
}