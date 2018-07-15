package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.SARMachineTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class TileEntityFluidIO extends SARMachineTileEntity {

	private int fluidTransferRate = 20;

	public TileEntityFluidIO() {
		super();
		this.setInventory(new InventoryMachine(
				new InventoryPieceItem(new ItemStackHandlerExtractSpecific(2), new int[] { 0, 0 }, new int[] { 0, 0 }),
				new InventoryPieceFluid(new MultiFluidTank(Fluid.BUCKET_VOLUME * 6, this, 1), 0, 0), null, null, null));
	}

	@Override
	public void onTick() {
		if(!this.inventory.getInputHandler().getStackInSlot(0).isEmpty()) {
			IFluidHandler itemFluid = this.inventory.getInputHandler().getStackInSlot(0)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(this.inventory.getInputTank().fill(itemFluid.drain(fluidTransferRate, false),
					false) == fluidTransferRate) {
				this.inventory.getInputTank().fill(itemFluid.drain(fluidTransferRate, true), true);
				markDirty();
				sendBlockUpdate();
			}
		}
		if(!this.inventory.getInputHandler().getStackInSlot(1).isEmpty()) {
			IFluidHandler itemFluid = this.inventory.getInputHandler().getStackInSlot(1)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(itemFluid.fill(this.inventory.getInputTank().drain(fluidTransferRate, false),
					false) == fluidTransferRate) {
				itemFluid.fill(this.inventory.getInputTank().drain(fluidTransferRate, true), true);
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
			return (T) this.inventory.getInputTank();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getName() {
		return "Fluid IO";
	}

}
