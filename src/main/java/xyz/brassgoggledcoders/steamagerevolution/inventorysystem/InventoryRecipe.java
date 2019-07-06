package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.ArrayList;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces.InventoryPieceTypedHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerFuel;

//TODO Cleaner way to define what handlers are available. Machines have fixed IOs, lists are somewhat the wrong thing...
public class InventoryRecipe extends InventoryBasic {
	public ArrayList<InventoryPieceTypedHandler<ItemStackHandler>> itemInputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceTypedHandler<FluidTankSmart>> fluidInputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceTypedHandler<ItemStackHandler>> itemOutputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceTypedHandler<FluidTankSmart>> fluidOutputPieces = new ArrayList<>();
	// TODO
	public InventoryPieceTypedHandler<FluidTankSingleSmart> steamPiece;
	public InventoryPieceTypedHandler<ItemStackHandlerFuel> fuelHandlerPiece;

	public InventoryRecipe addItemInput(int[] xPos, int[] yPos, ItemStackHandlerExtractSpecific handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		InventoryPieceTypedHandler<ItemStackHandler> iPiece = new InventoryPieceTypedHandler<ItemStackHandler>(
				IOType.INPUT, handler, xPos, yPos);
		itemInputPieces.add(iPiece);
		itemPieces.add(iPiece);
		return this;
	}

	public InventoryRecipe addItemOutput(int[] xPos, int[] yPos, ItemStackHandler handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		InventoryPieceTypedHandler<ItemStackHandler> iPiece = new InventoryPieceTypedHandler<ItemStackHandler>(
				IOType.OUTPUT, handler, xPos, yPos);
		itemOutputPieces.add(iPiece);
		itemPieces.add(iPiece);
		return this;
	}

	public InventoryRecipe addFluidInput(int xPos, int yPos, FluidTankSmart handler) {
		InventoryPieceTypedHandler<FluidTankSmart> fPiece = new InventoryPieceTypedHandler<FluidTankSmart>(IOType.INPUT,
				handler, xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.add(fPiece);
		return this;
	}

	public InventoryRecipe addFluidOutput(int xPos, int yPos, FluidTankSmart handler) {
		InventoryPieceTypedHandler<FluidTankSmart> fPiece = new InventoryPieceTypedHandler<FluidTankSmart>(
				IOType.OUTPUT, handler, xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.add(fPiece);
		return this;
	}

	public InventoryRecipe setSteamTank(int xPos, int yPos, int capacity, IMachineHasInventory parent) {
		steamPiece = new InventoryPieceTypedHandler<FluidTankSingleSmart>(IOType.POWER,
				new FluidTankSingleSmart(capacity, "steam", parent, IOType.POWER), xPos, yPos);
		fluidPieces.add(steamPiece);
		return this;
	}

	// TODO
	public InventoryRecipe setFuelHandler(int xPos, int yPos, ItemStackHandlerFuel handler) {
		fuelHandlerPiece = new InventoryPieceTypedHandler<ItemStackHandlerFuel>(IOType.POWER, handler, xPos, yPos);
		itemPieces.add(fuelHandlerPiece);
		return this;
	}

}
