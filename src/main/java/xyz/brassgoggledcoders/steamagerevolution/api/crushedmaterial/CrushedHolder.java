package xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

public class CrushedHolder implements ICrushedHolder {
	
	@Nullable
	protected CrushedStack stack; 
	protected int capacity;
	
	public CrushedHolder(int capacity) {
		this(null, capacity);
	}
	
	public CrushedHolder(CrushedStack material, int capacity) {
		this.stack = material;
		this.capacity = capacity;
	}

	@Override
	public CrushedStack getCrushed() {
		return stack;
	}

	@Override
	public int getAmount() {
		if(stack == null) {
			return 0;
		}
		return stack.amount;
	}

	@Override
	public int getHolderCapacity() {
		return capacity;
	}

	@Override
	public boolean fill(CrushedStack stackIn) {
		if((this.stack == null || stackIn.getMaterial() == this.stack.getMaterial()) && stackIn.amount <= (this.getHolderCapacity() - this.getAmount())) {
			this.stack = new CrushedStack(stackIn.material, this.getAmount() + stackIn.amount);
			return true;
		}
		return false;
	}

	@Override
	public boolean drain(ICrushedMaterial material, int toDrain) {
		if(this.stack != null && material == this.stack.getMaterial() && this.stack.amount >= toDrain) {
			this.stack.amount -= toDrain;
			if(stack.amount <= 0) {
				stack = null;
			}
			return true;
		}
		return false;
	}

	@Override
	public void setInternal(CrushedStack toSet) {
		this.stack = toSet;
	}

}
