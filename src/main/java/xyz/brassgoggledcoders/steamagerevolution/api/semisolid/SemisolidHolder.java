package xyz.brassgoggledcoders.steamagerevolution.api.semisolid;

import javax.annotation.Nullable;

public class SemisolidHolder implements ISemisolidHolder {
	
	@Nullable
	protected SemisolidStack stack; 
	protected int capacity;
	
	public SemisolidHolder(int capacity) {
		this(null, capacity);
	}
	
	public SemisolidHolder(SemisolidStack material, int capacity) {
		this.stack = material;
		this.capacity = capacity;
	}

	@Override
	public SemisolidStack getCrushed() {
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
	public boolean fill(SemisolidStack stackIn) {
		if((this.stack == null || stackIn.getMaterial() == this.stack.getMaterial()) && stackIn.amount <= (this.getHolderCapacity() - this.getAmount())) {
			this.stack = new SemisolidStack(stackIn.material, this.getAmount() + stackIn.amount);
			return true;
		}
		return false;
	}

	@Override
	public boolean drain(ISemisolid material, int toDrain) {
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
	public void setInternal(SemisolidStack toSet) {
		this.stack = toSet;
	}

}
