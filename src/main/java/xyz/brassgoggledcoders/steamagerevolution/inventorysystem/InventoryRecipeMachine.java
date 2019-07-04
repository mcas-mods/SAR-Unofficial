package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces.InventoryPieceHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.io.*;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ISmartStackCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class InventoryRecipeMachine
		implements IMachineInventory, INBTSerializable<NBTTagCompound>, ISmartTankCallback, ISmartStackCallback {

	public InventoryPieceHandler<ItemStackHandler> itemInput;
	public InventoryPieceHandler<? extends IFluidHandler> fluidInput;
	public InventoryPieceHandler<ItemStackHandler> itemOutput;
	public InventoryPieceHandler<? extends IFluidHandler> fluidOutput;
	public InventoryPieceHandler<FluidTankSmart> steamTank;
	public InventoryPieceProgressBar progressBar;

	public InventoryRecipeMachine setItemInput(int[] xPos, int[] yPos, ItemStackHandler handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		itemInput = new InventoryPieceHandler<ItemStackHandler>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setFluidInput(int xPos, int yPos, FluidTankSmart handler) {
		fluidInput = new InventoryPieceHandler<FluidTankSmart>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setFluidInputs(int[] xPos, int[] yPos, MultiFluidHandler handler) {
		if(xPos.length < handler.getNumberOfTanks() || yPos.length < handler.getNumberOfTanks()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of tanks");
		}
		fluidInput = new InventoryPieceHandler<MultiFluidHandler>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setItemOutput(int[] xPos, int[] yPos, ItemStackHandler handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		itemOutput = new InventoryPieceHandler<ItemStackHandler>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setFluidOutput(int xPos, int yPos, FluidTankSmart handler) {
		fluidOutput = new InventoryPieceHandler<FluidTankSmart>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setFluidOutputs(int[] xPos, int[] yPos, MultiFluidHandler handler) {
		if(xPos.length < handler.getNumberOfTanks() || yPos.length < handler.getNumberOfTanks()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of tanks");
		}
		fluidOutput = new InventoryPieceHandler<MultiFluidHandler>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setSteamTank(int xPos, int yPos, FluidTankSmart handler) {
		steamTank = new InventoryPieceHandler<FluidTankSmart>(IOType.POWER, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setProgressBar(InventoryPieceProgressBar bar) {
		progressBar = bar;
		return this;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		if(itemInput != null) {
			tag.setTag("itemInput", itemInput.getHandler().serializeNBT());
		}
		if(fluidInput != null) {
			tag.setTag("fluidInput", fluidInput.getHandler().serializeNBT());
		}
		if(itemOutput != null) {
			tag.setTag("itemOutput", itemOutput.getHandler().serializeNBT());
		}
		if(fluidOutput != null) {
			tag.setTag("fluidOutput", fluidOutput.getHandler().serializeNBT());
		}
		if(steamTank != null) {
			tag.setTag("steamTank", steamTank.getHandler().serializeNBT());
		}
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		if(tag.hasKey("itemInput") && itemInput != null) {
			itemInput.getHandler().deserializeNBT(tag.getCompoundTag("itemInput"));
		}
		if(tag.hasKey("fluidInput") && fluidInput != null) {
			fluidInput.getHandler().deserializeNBT(tag.getCompoundTag("fluidInput"));
		}
		if(tag.hasKey("itemOuput") && itemOutput != null) {
			itemOutput.getHandler().deserializeNBT(tag.getCompoundTag("itemOutput"));
		}
		if(tag.hasKey("fluidOutput") && fluidOutput != null) {
			fluidOutput.getHandler().deserializeNBT(tag.getCompoundTag("fluidOutput"));
		}
		if(tag.hasKey("steamTank") && steamTank != null) {
			steamTank.getHandler().deserializeNBT(tag.getCompoundTag("steamTank"));
		}
	}

	// Methods to enable dynamic tank sizes based on multiblock size
	public void setFluidInput(MultiFluidHandler newTank) {
		fluidInput = new InventoryPieceFluid(newTank, fluidInput.xPos, fluidInput.yPos);
		fluidInput.setTankType(IOType.INPUT);
	}

	public void setFluidOutput(MultiFluidHandler newTank) {
		fluidOutput = new InventoryPieceFluid(newTank, fluidOutput.xPos, fluidOutput.yPos);
		fluidOutput.setTankType(IOType.OUTPUT);
	}

	// Helpers for TE wrappers
	public ItemStackHandlerExtractSpecific getItemHandler(boolean output) {
		return output ? itemOutput.getHandler() : itemInput.getHandler();
	}

	public FluidTankSmart getFluidHandler(boolean output) {
		return output ? fluidOutput.getIO() : fluidInput.getIO();
	}

	public FluidTankSingleSmart getSteamHandler() {
		if(steamTank == null) {
			return null;
		}
		return (FluidTankSingleSmart) steamTank.getIO();
	}

	@Override
	public void onTankContentsChanged(FluidTankSmart tank, IOType type, IMachineHasInventory parent) {
		if(tank instanceof MultiFluidHandler) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketMultiFluidUpdate(parent.getMachinePos(), ((MultiFluidHandler) tank).fluids,
							IOType.getNetworkID(type)),
					parent.getMachinePos(), parent.getMachineWorld().provider.getDimension());
		}
		else {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(parent.getMachinePos(), tank.getFluid(), IOType.getNetworkID(type)),
					parent.getMachinePos(), parent.getMachineWorld().provider.getDimension());
		}
		// If we have a recipe, when a tank is changed, check input tank(s), if they
		// were emptied. You can't hotswap fluids like you can items.
		if(type == IOType.INPUT && getInputTank() != null && getInputTank().getFluidAmount() == 0) {
			parent.setCurrentRecipe(null);
			parent.setCurrentTicks(0);
		}
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		steamTank.getIO().setFluid(message.fluid);
	}

	@Override
	public void updateFluid(PacketMultiFluidUpdate message) {
		if(getInputTank() != null && IOType.INPUT.equals(IOType.getTypeFromID(message.id))) {
			getInputTank().fluids.clear();
			getInputTank().fluids.addAll(message.fluids);
		}
		else if(getOutputTank() != null && IOType.OUTPUT.equals(IOType.getTypeFromID(message.id))) {
			getOutputTank().fluids.clear();
			getOutputTank().fluids.addAll(message.fluids);
		}
	}

	@Override
	public void onContentsChanged(IOType type, int slot, IMachineHasInventory parent) {
		if(type.equals(IOType.INPUT)) {
			// If we have a recipe, when a slot is changed, check if it was changed to air,
			// or an item that doesn't match the recipe, and if so reset the recipe
			if(parent.getCurrentRecipe() != null && this.getInputHandler().getStackInSlot(slot).isEmpty()) {
				SteamAgeRevolution.instance.getLogger().devInfo("Resetting recipe due to item change");
				parent.setCurrentRecipe(null);
				parent.setCurrentTicks(0);
			}
		}
	}
}
