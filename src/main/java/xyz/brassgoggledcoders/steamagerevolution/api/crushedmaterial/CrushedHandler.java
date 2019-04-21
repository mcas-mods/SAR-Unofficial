package xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial;

import net.minecraft.nbt.NBTTagCompound;

public class CrushedHandler implements ICrushedHandler {
	
	protected ICrushedHolder[] holders;
	
	public CrushedHandler(ICrushedHolder... holders) {
		this.holders = holders;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound data = new NBTTagCompound();
		int i = 0;
		for(ICrushedHolder holder : holders) {
			if(holder.getCrushed() != null) {
				data.setTag(Integer.toString(i), holder.getCrushed().serializeNBT());
			}
			else {
				data.setTag(Integer.toString(i), new NBTTagCompound());
			}
			i++;
		}
		data.setInteger("size", i);
		return data;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		for(int i = 0; i < tag.getInteger("size"); i++) {
			NBTTagCompound subTag = (NBTTagCompound) tag.getTag(Integer.toString(i));
			if(!subTag.isEmpty()) {
				//TODO
				CrushedStack stack = new CrushedStack(null, 0);
				stack.deserializeNBT(subTag);
				holders[i].setInternal(stack);
			}
		}
	}

	@Override
	public ICrushedHolder[] getHolders() {
		return holders;
	}

	@Override
	public boolean fill(CrushedStack stackIn) {
		for(ICrushedHolder holder : holders) {
			if(holder.getCrushed() == null || stackIn.material == holder.getCrushed().getMaterial()) {
				if(stackIn.amount <= (holder.getHolderCapacity() - holder.getAmount())) {
					holder.fill(stackIn);
					this.onContentsChanged();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean drain(ICrushedMaterial material, int toDrain) {
		for(ICrushedHolder holder : holders) {
			if(holder.getCrushed() != null && material == holder.getCrushed().getMaterial() && holder.getCrushed().amount >= toDrain) {
				holder.drain(material, toDrain);
				this.onContentsChanged();
				return true;
			}
		}
		return false;
	}

}
