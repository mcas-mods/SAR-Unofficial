package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class InventoryMachine implements IMachineInventory, INBTSerializable<NBTTagCompound>, ISmartTankCallback {

	public InventoryPieceItem itemInput;
	public InventoryPieceFluid fluidInput;
	public InventoryPieceItem itemOutput;
	public InventoryPieceFluid fluidOutput;
	public InventoryPieceFluid steamTank;
	public InventoryPieceProgressBar progressBar;

	//@Deprecated
	public InventoryMachine(InventoryPieceFluid fluidInput, InventoryPieceFluid fluidOutput,
			InventoryPieceFluid steamTank) {
		this(null, fluidInput, null, fluidOutput, steamTank);
	}

	//@Deprecated
	public InventoryMachine(InventoryPieceItem itemInput, InventoryPieceFluid fluidInput, InventoryPieceItem itemOutput,
			InventoryPieceFluid fluidOutput, InventoryPieceFluid steamTank) {
		this.itemInput = itemInput;
		this.fluidInput = fluidInput;
		if(fluidInput != null) {
			this.fluidInput.setTankType(TankType.INPUT);
		}
		this.itemOutput = itemOutput;
		this.fluidOutput = fluidOutput;
		if(fluidOutput != null) {
			this.fluidOutput.setTankType(TankType.OUTPUT);
		}
		this.steamTank = steamTank;
		if(steamTank != null) {
			this.steamTank.setTankType(TankType.STEAM);
		}
	}
	
//	public static class Builder {
//		
//		public InventoryPieceItem itemInput;
//		public InventoryPieceFluid fluidInput;
//		public InventoryPieceItem itemOutput;
//		public InventoryPieceFluid fluidOutput;
//		public InventoryPieceFluid steamTank;
//		public InventoryPieceProgressBar progressBar;
//		
//		public Builder() {}
//		
//		public InventoryMachine build() {
//			InventoryMachine machine =  new InventoryMachine(itemInput, fluidInput, itemOutput, fluidOutput, steamTank);
//			if(progressBar != null) {
//				machine.setProgressBar(progressBar);
//			}
//			return machine;
//			
//		}
//	}

	public InventoryMachine setProgressBar(InventoryPieceProgressBar bar) {
		progressBar = bar;
		return this;
	}

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
		fluidInput.setTankType(TankType.INPUT);
	}

	public void setFluidOutput(MultiFluidTank newTank) {
		fluidOutput = new InventoryPieceFluid(newTank, fluidOutput.xPos, fluidOutput.yPos);
		fluidOutput.setTankType(TankType.OUTPUT);
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
	public void onTankContentsChanged(FluidTankSmart tank, TankType type, IMachineHasInventory parent) {
		if(tank instanceof MultiFluidTank) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketMultiFluidUpdate(parent.getMachinePos(), ((MultiFluidTank) tank).fluids,
							TankType.getNetworkID(type)),
					parent.getMachinePos(), parent.getMachineWorld().provider.getDimension());
		}
		else {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(parent.getMachinePos(), tank.getFluid(), TankType.getNetworkID(type)),
					parent.getMachinePos(), parent.getMachineWorld().provider.getDimension());
			// Only steam tank is a single type
			parent.setCurrentRecipe(null);
			parent.setCurrentTicks(0);
		}
		if(getInputTank() != null && TankType.INPUT.equals(type)) {
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
		if(getInputTank() != null && TankType.INPUT.equals(TankType.getTypeFromID(message.id))) {
			getInputTank().fluids.clear();
			getInputTank().fluids.addAll(message.fluids);
		}
		else if(getOutputTank() != null && TankType.OUTPUT.equals(TankType.getTypeFromID(message.id))) {
			getOutputTank().fluids.clear();
			getOutputTank().fluids.addAll(message.fluids);
		}
	}
}
