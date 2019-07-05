package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.ArrayList;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces.InventoryPieceTypedHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

//TODO Cleaner way to define what handlers are available. Machines have fixed IOs, lists are somewhat the wrong thing...
public class InventoryRecipe extends InventoryBasic {
	public ArrayList<InventoryPieceTypedHandler<ItemStackHandler>> itemInputs = new ArrayList<>();
	public ArrayList<InventoryPieceTypedHandler<FluidTankSmart>> fluidInputs = new ArrayList<>();
	public ArrayList<InventoryPieceTypedHandler<ItemStackHandler>> itemOutputs = new ArrayList<>();
	public ArrayList<InventoryPieceTypedHandler<FluidTankSmart>> fluidOutputs = new ArrayList<>();
	public InventoryPieceTypedHandler<FluidTankSingleSmart> steamTank;

	public InventoryRecipe addItemInput(int[] xPos, int[] yPos, ItemStackHandlerExtractSpecific handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		InventoryPieceTypedHandler<ItemStackHandler> iPiece = new InventoryPieceTypedHandler<ItemStackHandler>(
				IOType.INPUT, handler, xPos, yPos);
		itemInputs.add(iPiece);
		itemPieces.add(iPiece);
		return this;
	}

	public InventoryRecipe addItemOutput(int[] xPos, int[] yPos, ItemStackHandler handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		InventoryPieceTypedHandler<ItemStackHandler> iPiece = new InventoryPieceTypedHandler<ItemStackHandler>(
				IOType.OUTPUT, handler, xPos, yPos);
		itemOutputs.add(iPiece);
		itemPieces.add(iPiece);
		return this;
	}

	public InventoryRecipe addFluidInput(int xPos, int yPos, FluidTankSmart handler) {
		InventoryPieceTypedHandler<FluidTankSmart> fPiece = new InventoryPieceTypedHandler<FluidTankSmart>(IOType.INPUT,
				handler, xPos, yPos);
		fluidInputs.add(fPiece);
		fluidPieces.add(fPiece);
		return this;
	}

	public InventoryRecipe addFluidOutput(int xPos, int yPos, FluidTankSmart handler) {
		InventoryPieceTypedHandler<FluidTankSmart> fPiece = new InventoryPieceTypedHandler<FluidTankSmart>(
				IOType.OUTPUT, handler, xPos, yPos);
		fluidInputs.add(fPiece);
		fluidPieces.add(fPiece);
		return this;
	}

	public InventoryRecipe setSteamTank(int xPos, int yPos, FluidTankSingleSmart handler) {
		steamTank = new InventoryPieceTypedHandler<FluidTankSingleSmart>(IOType.POWER, handler, xPos, yPos);
		fluidPieces.add(steamTank);
		return this;
	}

}
