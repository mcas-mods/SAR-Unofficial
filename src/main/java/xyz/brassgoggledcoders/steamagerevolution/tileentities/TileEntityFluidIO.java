package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;

public class TileEntityFluidIO extends TileEntityInventory<InventoryBasic> {

	private int fluidTransferRate = 20;

	public TileEntityFluidIO() {
		super();
		setInventory(new InventoryBasic(this)
				.addItemPiece("items", new int[] { 25, 134 }, new int[] { 33, 33 },
						new ItemStackHandlerSync("items", 2, this))
				.addFluidPiece("tank", 78, 11, Fluid.BUCKET_VOLUME * 6));
	}

	@Override
	public void update() {
		if(!inventory.getItemPiece("items").getHandler().getStackInSlot(0).isEmpty()) {
			IFluidHandler itemFluid = inventory.getItemPiece("items").getHandler().getStackInSlot(0)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(itemFluid != null && itemFluid.drain(fluidTransferRate, false) != null && inventory.getFluidPiece("tank")
					.getHandler().fill(itemFluid.drain(fluidTransferRate, false), false) == fluidTransferRate) {
				inventory.getFluidPiece("tank").getHandler().fill(itemFluid.drain(fluidTransferRate, true), true);
				markMachineDirty();
				sendBlockUpdate();
			}
		}
		if(!inventory.getItemPiece("items").getHandler().getStackInSlot(1).isEmpty()) {
			IFluidHandler itemFluid = inventory.getItemPiece("items").getHandler().getStackInSlot(1)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(itemFluid != null && inventory.getFluidPiece("tank").getHandler().getFluidAmount() > 0
					&& itemFluid.fill(inventory.getFluidPiece("tank").getHandler().drain(fluidTransferRate, false),
							false) == fluidTransferRate) {
				itemFluid.fill(inventory.getFluidPiece("tank").getHandler().drain(fluidTransferRate, true), true);
				markMachineDirty();
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
			return (T) inventory.getFluidPiece("tank").getHandler();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getName() {
		return "Fluid IO";
	}

}
