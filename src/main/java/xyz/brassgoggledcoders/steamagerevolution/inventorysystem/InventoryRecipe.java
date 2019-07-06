package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.ArrayList;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceTypedHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;

//TODO Cleaner way to define what handlers are available. Machines have fixed IOs, lists are somewhat the wrong thing...
public class InventoryRecipe extends InventoryBasic {
	public ArrayList<InventoryPieceTypedHandler<ItemStackHandlerSmart>> itemInputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceTypedHandler<FluidTankSmart>> fluidInputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceTypedHandler<ItemStackHandlerSmart>> itemOutputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceTypedHandler<FluidTankSmart>> fluidOutputPieces = new ArrayList<>();
	// TODO
	public InventoryPieceTypedHandler<FluidTankSingleSmart> steamPiece;
	public InventoryPieceTypedHandler<ItemStackHandlerFuel> fuelHandlerPiece;

	public InventoryRecipe addItemInput(String name, int[] xPos, int[] yPos, ItemStackHandlerSmart handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		InventoryPieceTypedHandler<ItemStackHandlerSmart> iPiece = new InventoryPieceTypedHandler<ItemStackHandlerSmart>(
				name, IOType.INPUT, handler, xPos, yPos);
		itemInputPieces.add(iPiece);
		itemPieces.put(name, iPiece);
		return this;
	}

	public InventoryRecipe addItemOutput(String name, int[] xPos, int[] yPos, ItemStackHandlerSmart handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		InventoryPieceTypedHandler<ItemStackHandlerSmart> iPiece = new InventoryPieceTypedHandler<ItemStackHandlerSmart>(
				name, IOType.OUTPUT, handler, xPos, yPos);
		itemOutputPieces.add(iPiece);
		itemPieces.put(name, iPiece);
		return this;
	}

	public InventoryRecipe addFluidInput(String name, int xPos, int yPos, FluidTankSmart handler) {
		InventoryPieceTypedHandler<FluidTankSmart> fPiece = new InventoryPieceTypedHandler<FluidTankSmart>(name,
				IOType.INPUT, handler, xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.put(name, fPiece);
		return this;
	}

	public InventoryRecipe addFluidOutput(String name, int xPos, int yPos, FluidTankSmart handler) {
		InventoryPieceTypedHandler<FluidTankSmart> fPiece = new InventoryPieceTypedHandler<FluidTankSmart>(name,
				IOType.OUTPUT, handler, xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.put(name, fPiece);
		return this;
	}

	public InventoryRecipe setSteamTank(int xPos, int yPos, int capacity, IMachineHasInventory parent) {
		steamPiece = new InventoryPieceTypedHandler<FluidTankSingleSmart>("steamTank", IOType.POWER,
				new FluidTankSingleSmart(capacity, "steam", parent, IOType.POWER), xPos, yPos);
		fluidPieces.put("steamTank", steamPiece);
		return this;
	}

	// TODO
	public InventoryRecipe setFuelHandler(int xPos, int yPos, ItemStackHandlerFuel handler) {
		fuelHandlerPiece = new InventoryPieceTypedHandler<ItemStackHandlerFuel>("fuel", IOType.POWER, handler, xPos,
				yPos);
		itemPieces.put("fuel", fuelHandlerPiece);
		return this;
	}

	// TODO
	@Override
	public InventoryRecipe setProgressBar(InventoryPieceProgressBar bar) {
		super.setProgressBar(bar);
		return this;
	}

}
