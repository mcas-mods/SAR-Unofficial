package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces.InventoryPieceHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ISmartStackCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

@SuppressWarnings("rawtypes")
public class InventoryRecipeMachine
		implements IMachineInventory, INBTSerializable<NBTTagCompound>, ISmartTankCallback, ISmartStackCallback {

	public InventoryPieceHandler<ItemStackHandlerExtractSpecific> itemInputPiece;
	public InventoryPieceHandler<? extends FluidTankSmart> fluidInputPiece;
	public InventoryPieceHandler<ItemStackHandler> itemOutputPiece;
	public InventoryPieceHandler<? extends FluidTankSmart> fluidOutputPiece;
	public InventoryPieceHandler<FluidTankSingleSmart> steamTankPiece;
	public InventoryPieceProgressBar progressBar;

	public InventoryRecipeMachine setItemInput(Pair<int[], int[]> xNy, ItemStackHandlerExtractSpecific handler) {
		this.setItemInput(xNy.getLeft(), xNy.getRight(), handler);
		return this;
	}

	public InventoryRecipeMachine setItemInput(int[] xPos, int[] yPos, ItemStackHandlerExtractSpecific handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		itemInputPiece = new InventoryPieceHandler<ItemStackHandlerExtractSpecific>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setFluidInput(int xPos, int yPos, FluidTankSmart handler) {
		fluidInputPiece = new InventoryPieceHandler<FluidTankSmart>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setFluidInputs(int[] xPos, int[] yPos, MultiFluidTank handler) {
		if(xPos.length < handler.getMaxFluids() || yPos.length < handler.getMaxFluids()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of tanks");
		}
		fluidInputPiece = new InventoryPieceHandler<MultiFluidTank>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setItemOutput(int[] xPos, int[] yPos, ItemStackHandler handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		itemOutputPiece = new InventoryPieceHandler<ItemStackHandler>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setFluidOutput(int xPos, int yPos, FluidTankSmart handler) {
		fluidOutputPiece = new InventoryPieceHandler<FluidTankSmart>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setFluidOutputs(int[] xPos, int[] yPos, MultiFluidTank handler) {
		if(xPos.length < handler.getMaxFluids() || yPos.length < handler.getMaxFluids()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of tanks");
		}
		fluidOutputPiece = new InventoryPieceHandler<MultiFluidTank>(IOType.INPUT, handler, xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setSteamTank(int xPos, int yPos, int capacity, IMachineHasInventory<?> parent) {
		steamTankPiece = new InventoryPieceHandler<FluidTankSingleSmart>(IOType.POWER,
				new FluidTankSingleSmart(capacity, "steam", parent), xPos, yPos);
		return this;
	}

	public InventoryRecipeMachine setProgressBar(InventoryPieceProgressBar bar) {
		progressBar = bar;
		return this;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		if(itemInputPiece != null) {
			tag.setTag("itemInput", itemInputPiece.getHandler().serializeNBT());
		}
		if(fluidInputPiece != null) {
			tag.setTag("fluidInput", fluidInputPiece.getHandler().serializeNBT());
		}
		if(itemOutputPiece != null) {
			tag.setTag("itemOutput", itemOutputPiece.getHandler().serializeNBT());
		}
		if(fluidOutputPiece != null) {
			tag.setTag("fluidOutput", fluidOutputPiece.getHandler().serializeNBT());
		}
		if(steamTankPiece != null) {
			tag.setTag("steamTank", steamTankPiece.getHandler().serializeNBT());
		}
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		if(tag.hasKey("itemInput") && itemInputPiece != null) {
			itemInputPiece.getHandler().deserializeNBT(tag.getCompoundTag("itemInput"));
		}
		if(tag.hasKey("fluidInput") && fluidInputPiece != null) {
			fluidInputPiece.getHandler().deserializeNBT(tag.getCompoundTag("fluidInput"));
		}
		if(tag.hasKey("itemOuput") && itemOutputPiece != null) {
			itemOutputPiece.getHandler().deserializeNBT(tag.getCompoundTag("itemOutput"));
		}
		if(tag.hasKey("fluidOutput") && fluidOutputPiece != null) {
			fluidOutputPiece.getHandler().deserializeNBT(tag.getCompoundTag("fluidOutput"));
		}
		if(tag.hasKey("steamTank") && steamTankPiece != null) {
			steamTankPiece.getHandler().deserializeNBT(tag.getCompoundTag("steamTank"));
		}
	}

	// Methods to enable dynamic tank sizes based on multiblock size
	@Deprecated
	public void setFluidInput(FluidTankSmart newTank) {
		fluidInputPiece = new InventoryPieceHandler<FluidTankSmart>(IOType.INPUT, newTank, fluidInputPiece.xPos,
				fluidInputPiece.yPos);
	}

	@Deprecated
	public void setFluidOutput(FluidTankSmart newTank) {
		fluidOutputPiece = new InventoryPieceHandler<FluidTankSmart>(IOType.OUTPUT, newTank, fluidOutputPiece.xPos,
				fluidOutputPiece.yPos);
	}

	@Override
	public void onTankContentsChanged(FluidTankSmart tank, IOType type, IMachineHasInventory parent) {
		if(tank instanceof MultiFluidTank) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketMultiFluidUpdate(parent.getMachinePos(), ((MultiFluidTank) tank).fluids, type.networkID),
					parent.getMachinePos(), parent.getMachineWorld().provider.getDimension());
		}
		else {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(parent.getMachinePos(), tank.getFluid(), type.networkID),
					parent.getMachinePos(), parent.getMachineWorld().provider.getDimension());
		}
		// If we have a recipe, when a tank is changed, check input tank(s), if they
		// were emptied. You can't hotswap fluids like you can items.
		// if(type == IOType.INPUT && getInputTank() != null &&
		// getInputTank().getFluidAmount() == 0) {
		// parent.setCurrentRecipe(null);
		// parent.setCurrentTicks(0);
		// }
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		if(getInputTank() != null && IOType.INPUT.networkID == message.typeID) {
			this.getInputTank().setFluid(message.fluid);
		}
		else if(getInputTank() != null && IOType.OUTPUT.networkID == message.typeID) {
			this.getOutputTank().setFluid(message.fluid);
		}
		else if(getSteamTank() != null && IOType.POWER.networkID == message.typeID) {
			this.getSteamTank().setFluid(message.fluid);
		}
	}

	@Override
	public void updateFluid(PacketMultiFluidUpdate message) {
		if(getInputTank() != null && IOType.INPUT.networkID == message.typeID) {
			((MultiFluidTank) this.getInputTank()).fluids.clear();
			((MultiFluidTank) this.getInputTank()).fluids.addAll(message.fluids);
		}
		else if(getInputTank() != null && IOType.OUTPUT.networkID == message.typeID) {
			((MultiFluidTank) this.getOutputTank()).fluids.clear();
			((MultiFluidTank) this.getOutputTank()).fluids.addAll(message.fluids);
		}
	}

	@Override
	public void onContentsChanged(IOType type, int slot, IMachineHasInventory parent) {
		// if(type.equals(IOType.INPUT)) {
		// // If we have a recipe, when a slot is changed, check if it was changed to
		// air,
		// // or an item that doesn't match the recipe, and if so reset the recipe
		// if(parent.getCurrentRecipe() != null &&
		// this.getInputHandler().getStackInSlot(slot).isEmpty()) {
		// SteamAgeRevolution.instance.getLogger().devInfo("Resetting recipe due to item
		// change");
		// parent.setCurrentRecipe(null);
		// parent.setCurrentTicks(0);
		// }
		// }
	}

	@Override
	public ItemStackHandlerExtractSpecific getInputHandler() {
		return this.itemInputPiece.getHandler();
	}

	@Override
	public FluidTankSmart getInputTank() {
		return fluidInputPiece.getHandler();
	}

	@Override
	public ItemStackHandler getOutputHandler() {
		return this.itemOutputPiece.getHandler();
	}

	@Override
	public FluidTankSmart getOutputTank() {
		return fluidOutputPiece.getHandler();
	}

	@Override
	public FluidTankSingleSmart getSteamTank() {
		return steamTankPiece.getHandler();
	}

	// Helpers for TE wrappers
	public FluidTankSmart getFluidHandler(boolean output) {
		return output ? fluidOutputPiece.getHandler() : fluidInputPiece.getHandler();
	}

	public ItemStackHandler getItemHandler(boolean output) {
		return output ? itemOutputPiece.getHandler() : itemInputPiece.getHandler();
	}
}
