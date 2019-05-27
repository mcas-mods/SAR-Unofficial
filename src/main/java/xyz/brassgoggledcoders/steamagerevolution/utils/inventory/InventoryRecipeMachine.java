package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ISmartStackCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class InventoryRecipeMachine
		implements IMachineInventory, INBTSerializable<NBTTagCompound>, ISmartTankCallback, ISmartStackCallback {

	public InventoryPieceItem itemInput;
	public InventoryPieceFluid fluidInput;
	public InventoryPieceItem itemOutput;
	public InventoryPieceFluid fluidOutput;
	public InventoryPieceFluid steamTank;
	public InventoryPieceProgressBar progressBar;

	public InventoryRecipeMachine(InventoryPieceFluid fluidInput, InventoryPieceFluid fluidOutput,
			InventoryPieceFluid steamTank) {
		this(null, fluidInput, null, fluidOutput, steamTank);
	}

	public InventoryRecipeMachine(InventoryPieceItem itemInput, InventoryPieceFluid fluidInput,
			InventoryPieceItem itemOutput, InventoryPieceFluid fluidOutput, InventoryPieceFluid steamTank) {
		this.itemInput = itemInput;
		if(itemInput != null) {
			this.itemInput.setType(IOTYPE.INPUT);
		}
		this.fluidInput = fluidInput;
		if(fluidInput != null) {
			this.fluidInput.setTankType(IOTYPE.INPUT);
		}
		this.itemOutput = itemOutput;
		this.fluidOutput = fluidOutput;
		if(fluidOutput != null) {
			this.fluidOutput.setTankType(IOTYPE.OUTPUT);
		}
		this.steamTank = steamTank;
		if(steamTank != null) {
			this.steamTank.setTankType(IOTYPE.STEAM);
		}
	}

	public InventoryRecipeMachine setProgressBar(InventoryPieceProgressBar bar) {
		progressBar = bar;
		return this;
	}

	// TODO Move away from concept of fixed inputs and outputs and allow arbitrary
	// numbers of handlers which may or may not be designated as IOs for recipe
	// purposes
	@Override
	public ItemStackHandlerExtractSpecific getInputHandler() {
		if(itemInput == null) {
			return null;
		}
		return itemInput.getHandler();
	}

	@Override
	public MultiFluidTank getInputTank() {
		if(fluidInput == null) {
			return null;
		}
		// TODO Unsafe cast
		return (MultiFluidTank) fluidInput.getHandler();
	}

	@Override
	public ItemStackHandler getOutputHandler() {
		if(itemOutput == null) {
			return null;
		}
		return itemOutput.getHandler();
	}

	@Override
	public MultiFluidTank getOutputTank() {
		// TODO Unsafe cast
		if(fluidOutput == null) {
			return null;
		}
		return (MultiFluidTank) fluidOutput.getHandler();
	}

	@Override
	public FluidTankSingleSmart getSteamTank() {
		if(steamTank == null) {
			return null;
		}
		return (FluidTankSingleSmart) steamTank.getHandler();
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		if(itemInput != null) {
			tag.setTag("itemInput", itemInput.handler.serializeNBT());
		}
		if(fluidInput != null) {
			tag.setTag("fluidInput", fluidInput.handler.writeToNBT(new NBTTagCompound()));
		}
		if(itemOutput != null) {
			tag.setTag("itemOutput", itemOutput.handler.serializeNBT());
		}
		if(fluidOutput != null) {
			tag.setTag("fluidOutput", fluidOutput.handler.writeToNBT(new NBTTagCompound()));
		}
		if(steamTank != null) {
			tag.setTag("steamTank", steamTank.handler.writeToNBT(new NBTTagCompound()));
		}
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		if(tag.hasKey("itemInput") && itemInput != null) {
			itemInput.handler.deserializeNBT(tag.getCompoundTag("itemInput"));
		}
		if(tag.hasKey("fluidInput") && fluidInput != null) {
			fluidInput.handler.readFromNBT(tag.getCompoundTag("fluidInput"));
		}
		if(tag.hasKey("itemOuput") && itemOutput != null) {
			itemOutput.handler.deserializeNBT(tag.getCompoundTag("itemOutput"));
		}
		if(tag.hasKey("fluidOutput") && fluidOutput != null) {
			fluidOutput.handler.readFromNBT(tag.getCompoundTag("fluidOutput"));
		}
		if(tag.hasKey("steamTank") && steamTank != null) {
			steamTank.handler.readFromNBT(tag.getCompoundTag("steamTank"));
		}
	}

	// Methods to enable dynamic tank sizes based on multiblock size
	public void setFluidInput(MultiFluidTank newTank) {
		fluidInput = new InventoryPieceFluid(newTank, fluidInput.xPos, fluidInput.yPos);
		fluidInput.setTankType(IOTYPE.INPUT);
	}

	public void setFluidOutput(MultiFluidTank newTank) {
		fluidOutput = new InventoryPieceFluid(newTank, fluidOutput.xPos, fluidOutput.yPos);
		fluidOutput.setTankType(IOTYPE.OUTPUT);
	}

	// Helpers for TE wrappers
	public ItemStackHandlerExtractSpecific getItemHandler(boolean output) {
		return output ? itemOutput.getHandler() : itemInput.getHandler();
	}

	public FluidTankSmart getFluidHandler(boolean output) {
		return output ? fluidOutput.getHandler() : fluidInput.getHandler();
	}

	public FluidTankSingleSmart getSteamHandler() {
		if(steamTank == null) {
			return null;
		}
		return (FluidTankSingleSmart) steamTank.getHandler();
	}

	@Override
	public void onTankContentsChanged(FluidTankSmart tank, IOTYPE type, IMachineHasInventory parent) {
		if(tank instanceof MultiFluidTank) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketMultiFluidUpdate(parent.getMachinePos(), ((MultiFluidTank) tank).fluids,
							IOTYPE.getNetworkID(type)),
					parent.getMachinePos(), parent.getMachineWorld().provider.getDimension());
		}
		else {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(parent.getMachinePos(), tank.getFluid(), IOTYPE.getNetworkID(type)),
					parent.getMachinePos(), parent.getMachineWorld().provider.getDimension());
		}
		// If we have a recipe, when a tank is changed, check input tank(s), if they
		// were emptied. You can't hotswap fluids like you can items.
		if(type == IOTYPE.INPUT && getInputTank() != null && getInputTank().getFluidAmount() == 0) {
			parent.setCurrentRecipe(null);
			parent.setCurrentTicks(0);
		}
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		steamTank.getHandler().setFluid(message.fluid);
	}

	@Override
	public void updateFluid(PacketMultiFluidUpdate message) {
		if(getInputTank() != null && IOTYPE.INPUT.equals(IOTYPE.getTypeFromID(message.id))) {
			getInputTank().fluids.clear();
			getInputTank().fluids.addAll(message.fluids);
		}
		else if(getOutputTank() != null && IOTYPE.OUTPUT.equals(IOTYPE.getTypeFromID(message.id))) {
			getOutputTank().fluids.clear();
			getOutputTank().fluids.addAll(message.fluids);
		}
	}

	@Override
	public void onContentsChanged(IOTYPE type, int slot, IMachineHasInventory parent) {
		if(type.equals(IOTYPE.INPUT)) {
			// If we have a recipe, when a slot is changed, check if it was changed to air,
			// or an item that doesn't match the recipe, and if so reset the recipe
			if(parent.getCurrentRecipe() != null && this.getInputHandler().getStackInSlot(slot).isEmpty()) {
				SteamAgeRevolution.instance.getLogger().devInfo("Resetting recipe due to item change");
				parent.setCurrentRecipe(null);
				parent.setCurrentTicks(0);
			}
		}
	}

	@Override
	public void updateStack(PacketItemUpdate message) {
		// TODO Auto-generated method stub

	}
}
