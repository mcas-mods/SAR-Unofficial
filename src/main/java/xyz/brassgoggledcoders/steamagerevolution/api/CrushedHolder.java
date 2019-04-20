package xyz.brassgoggledcoders.steamagerevolution.api;

import javax.annotation.Nullable;

public class CrushedHolder implements ICrushedHolder {
	
	@Nullable
	protected CrushedStack stack; 
	protected int capacity;
	
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
		if(stackIn.material == this.stack.material && stackIn.amount <= (this.getHolderCapacity() - this.getAmount())) {
			this.stack.amount += stackIn.amount;
			return true;
		}
		return false;
	}

	@Override
	public boolean drain(ICrushedMaterial material, int toDrain) {
		if(material == this.stack.material && this.stack.amount >= toDrain) {
			this.stack.amount -= toDrain;
			if(stack.amount <= 0) {
				stack = null;
			}
			return true;
		}
		return false;
	}

}
