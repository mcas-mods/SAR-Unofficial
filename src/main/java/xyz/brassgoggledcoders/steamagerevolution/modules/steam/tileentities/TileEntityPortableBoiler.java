package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ModuleSteam;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.SARMachineTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class TileEntityPortableBoiler extends SARMachineTileEntity {

	int currentBurnTime = 0;

	public TileEntityPortableBoiler() {
		setInventory(new InventoryMachine(new InventoryPieceItem(new ItemStackHandlerExtractSpecific(1), 0, 0),
				new InventoryPieceFluid(new MultiFluidTank(Fluid.BUCKET_VOLUME, this, 1), 0, 0), null, null,
				new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME, "steam", this), 0, 0)));
	}

	@Override
	public String getName() {
		return "Portable Boiler";
	}

	public ItemStack createDrop() {
		ItemStack stack = new ItemStack(Item.getItemFromBlock(ModuleSteam.portable_boiler));
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("teData", serializeNBT());
		stack.setTagCompound(tag);
		return stack;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) inventory.getInputTank();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean canRun() {
		return inventory.getInputTank().getFluidAmount() != 0;
	}

	@Override
	public void onActiveTick() {
		if(currentTicks == 0) {
			if(!inventory.getInputHandler().getStackInSlot(0).isEmpty()) {
				currentTicks = TileEntityFurnace.getItemBurnTime(inventory.getInputHandler().getStackInSlot(0));
				inventory.getInputHandler().extractItem(0, 1, false);
			}
		}
		else {
			int fluidAmount = Fluid.BUCKET_VOLUME / 20;
			inventory.getInputTank().drain(fluidAmount, true);
			inventory.getSteamTank().fill(FluidRegistry.getFluidStack("steam", fluidAmount), true);
			currentTicks--;
		}
	}

}
