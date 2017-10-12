package xyz.brassgoggledcoders.steamagerevolution.utils;

import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/**
 * @author BluSunrize - 20.02.2017
 */
// FIXME
public class MultiFluidTank extends FluidTankSmart {
	public ArrayList<FluidStack> fluids = new ArrayList<>();
	private final int capacity;

	public MultiFluidTank(int capacity, MultiblockControllerBase parent) {
		super(capacity, parent);
		this.capacity = capacity;
	}

	@Override
	public MultiFluidTank readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("fluids")) {
			fluids.clear();
			NBTTagList tagList = nbt.getTagList("fluids", 10);
			for(int i = 0; i < tagList.tagCount(); i++) {
				FluidStack fs = FluidStack.loadFluidStackFromNBT(tagList.getCompoundTagAt(i));
				if(fs != null)
					this.fluids.add(fs);
			}
		}
		return this;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList tagList = new NBTTagList();
		for(FluidStack fs : this.fluids)
			if(fs != null)
				tagList.appendTag(fs.writeToNBT(new NBTTagCompound()));
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
		for(FluidStack fs : fluids)
			sum += fs.amount;
		return sum;
	}

	@Override
	public int getCapacity() {
		return this.capacity;
	}

	@Override
	public FluidTankInfo getInfo() {
		FluidStack fs = getFluid();
		int capacity = this.capacity - getFluidAmount();
		if(fs != null)
			capacity += fs.amount;
		return new FluidTankInfo(fs, capacity);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[0];
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		int space = this.capacity - getFluidAmount();
		int toFill = Math.min(resource.amount, space);
		if(!doFill)
			return toFill;
		for(FluidStack fs : this.fluids)
			if(fs.isFluidEqual(resource)) {
				fs.amount += toFill;
				onContentsChanged();
				return toFill;
			}
		this.fluids.add(copyFluidStackWithAmount(resource, toFill));
		return toFill;

	}

	public static FluidStack copyFluidStackWithAmount(FluidStack stack, int amount) {
		if(stack == null)
			return null;
		FluidStack fs = new FluidStack(stack, amount);
		return fs;
	}

	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(this.fluids.isEmpty())
			return null;
		Iterator<FluidStack> it = this.fluids.iterator();
		while(it.hasNext()) {
			FluidStack fs = it.next();
			if(fs.isFluidEqual(resource)) {
				int amount = Math.min(resource.amount, fs.amount);
				if(doDrain) {
					onContentsChanged();
					fs.amount -= amount;
					if(fs.amount <= 0)
						it.remove();
				}
				return copyFluidStackWithAmount(resource, amount);
			}
		}
		return null;
	}

	public static FluidStack drain(int remove, FluidStack removeFrom, Iterator<FluidStack> removeIt, boolean doDrain) {
		int amount = Math.min(remove, removeFrom.amount);
		if(doDrain) {
			removeFrom.amount -= amount;
			if(removeFrom.amount <= 0)
				removeIt.remove();
		}
		return copyFluidStackWithAmount(removeFrom, amount);
	}

	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(this.fluids.isEmpty())
			return null;
		return drain(new FluidStack(getFluid(), maxDrain), doDrain);
	}
}