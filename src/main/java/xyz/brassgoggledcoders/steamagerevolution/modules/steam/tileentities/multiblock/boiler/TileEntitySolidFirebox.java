package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;

public class TileEntitySolidFirebox extends TileEntityBasicBoilerPart implements ITickableMultiblockPart {

	private IItemHandler inventory;
	private int burnTime;

	public TileEntitySolidFirebox() {
		super();
		inventory = new ItemStackHandler();
	}

	@Override
	public boolean tick() {
		if(burnTime == 0) {
			if(ItemStackUtils.isItemNonNull(inventory.getStackInSlot(0))
					&& TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0)) > 0) {
				burnTime = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0));
				// TODO
				inventory.getStackInSlot(0).stackSize--;
			}
		}
		else
			burnTime--;

		return false;
	}

	@Override
	protected void readFromDisk(NBTTagCompound data) {
		this.setBurnTime(data.getInteger("burnTime"));
		super.readFromDisk(data);
	};

	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		data.setInteger("burnTime", getBurnTime());
		return super.writeToDisk(data);
	};

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) inventory;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	public int getBurnTime() {
		return burnTime;
	}

	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

}
