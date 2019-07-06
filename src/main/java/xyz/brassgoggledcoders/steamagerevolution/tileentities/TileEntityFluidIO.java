package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidHandlerMulti;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class TileEntityFluidIO extends RecipeTileEntity {

	private int fluidTransferRate = 20;

	public TileEntityFluidIO() {
		super();
		setInventory(new InventoryBasic()
				.setItemInput(new int[] { 25, 134 }, new int[] { 33, 33 }, new ItemStackHandlerExtractSpecific(2))
				.setFluidInput(78, 11, new FluidHandlerMulti(this, IOType.INPUT, Fluid.BUCKET_VOLUME * 6)));
	}

	@Override
	public void onTick() {
		if(!inventory.getInputItemHandler().getStackInSlot(0).isEmpty()) {
			IFluidHandler itemFluid = inventory.getInputItemHandler().getStackInSlot(0)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(itemFluid != null && itemFluid.drain(fluidTransferRate, false) != null
					&& inventory.getInputFluidHandler().fill(itemFluid.drain(fluidTransferRate, false),
							false) == fluidTransferRate) {
				inventory.getInputFluidHandler().fill(itemFluid.drain(fluidTransferRate, true), true);
				markDirty();
				sendBlockUpdate();
			}
		}
		if(!inventory.getInputItemHandler().getStackInSlot(1).isEmpty()) {
			IFluidHandler itemFluid = inventory.getInputItemHandler().getStackInSlot(1)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(itemFluid != null && inventory.getInputFluidHandler().getTank().getFluidAmount() > 0
					&& itemFluid.fill(inventory.getInputFluidHandler().drain(fluidTransferRate, false),
							false) == fluidTransferRate) {
				itemFluid.fill(inventory.getInputFluidHandler().drain(fluidTransferRate, true), true);
				markDirty();
				sendBlockUpdate();
			}
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) inventory.getInputFluidHandler();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getName() {
		return "Fluid IO";
	}

}
