package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryRecipeMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.SARMachineTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class TileEntityFluidIO extends SARMachineTileEntity {

	private int fluidTransferRate = 20;

	public TileEntityFluidIO() {
		super();
		setInventory(new InventoryRecipeMachine(
				new InventoryPieceItem(new ItemStackHandlerExtractSpecific(2), new int[] { 25, 134 },
						new int[] { 33, 33 }),
				new InventoryPieceFluid(new MultiFluidTank(Fluid.BUCKET_VOLUME * 6, this, 1), 78, 11), null, null,
				null));
	}

	@Override
	public void onTick() {
		if (!inventory.getInputHandler().getStackInSlot(0).isEmpty()) {
			IFluidHandler itemFluid = inventory.getInputHandler().getStackInSlot(0)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if (itemFluid != null && itemFluid.drain(fluidTransferRate, false) != null && inventory.getInputTank()
					.fill(itemFluid.drain(fluidTransferRate, false), false) == fluidTransferRate) {
				inventory.getInputTank().fill(itemFluid.drain(fluidTransferRate, true), true);
				markDirty();
				sendBlockUpdate();
			}
		}
		if (!inventory.getInputHandler().getStackInSlot(1).isEmpty()) {
			IFluidHandler itemFluid = inventory.getInputHandler().getStackInSlot(1)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if (itemFluid != null && inventory.getInputTank().getFluidAmount() > 0 && itemFluid
					.fill(inventory.getInputTank().drain(fluidTransferRate, false), false) == fluidTransferRate) {
				itemFluid.fill(inventory.getInputTank().drain(fluidTransferRate, true), true);
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
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) inventory.getInputTank();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getName() {
		return "Fluid IO";
	}

}
