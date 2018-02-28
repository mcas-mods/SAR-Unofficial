package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

// TODO NBT
public class TileEntitySorterBuffer extends TileEntitySorterPart {
	public int color;
	public ItemStackHandler inventory = new ItemStackHandler(6);

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.isConnected())
			return true;
		return super.hasCapability(capObject, side);
	}

	@Override
	public void readFromDisk(NBTTagCompound compound) {
		color = compound.getInteger("color");
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromDisk(compound);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound compound) {
		compound.setInteger("color", color);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToDisk(compound);
	}
}
