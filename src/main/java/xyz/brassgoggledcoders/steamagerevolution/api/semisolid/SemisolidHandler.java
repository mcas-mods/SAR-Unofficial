package xyz.brassgoggledcoders.steamagerevolution.api.semisolid;

import net.minecraft.nbt.NBTTagCompound;

public class SemisolidHandler implements ISemisolidHandler {

	protected ISemisolidHolder[] holders;

	public SemisolidHandler(ISemisolidHolder... holders) {
		this.holders = holders;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound data = new NBTTagCompound();
		int i = 0;
		for (ISemisolidHolder holder : holders) {
			if (holder.getCrushed() != null) {
				data.setTag(Integer.toString(i), holder.getCrushed().serializeNBT());
			} else {
				data.setTag(Integer.toString(i), new NBTTagCompound());
			}
			i++;
		}
		data.setInteger("size", i);
		return data;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		for (int i = 0; i < tag.getInteger("size"); i++) {
			NBTTagCompound subTag = (NBTTagCompound) tag.getTag(Integer.toString(i));
			if (!subTag.isEmpty()) {
				// TODO
				SemisolidStack stack = new SemisolidStack(null, 0);
				stack.deserializeNBT(subTag);
				holders[i].setInternal(stack);
			}
		}
	}

	@Override
	public ISemisolidHolder[] getHolders() {
		return holders;
	}

	@Override
	public boolean fill(SemisolidStack stackIn) {
		for (ISemisolidHolder holder : holders) {
			if (holder.getCrushed() == null || stackIn.material == holder.getCrushed().getMaterial()) {
				if (stackIn.amount <= (holder.getHolderCapacity() - holder.getAmount())) {
					holder.fill(stackIn);
					this.onContentsChanged();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean drain(ISemisolid material, int toDrain) {
		for (ISemisolidHolder holder : holders) {
			if (holder.getCrushed() != null && material == holder.getCrushed().getMaterial()
					&& holder.getCrushed().amount >= toDrain) {
				holder.drain(material, toDrain);
				this.onContentsChanged();
				return true;
			}
		}
		return false;
	}

}
