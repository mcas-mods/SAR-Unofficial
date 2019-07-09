package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.ArrayList;

import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.*;

//TODO Cleaner way to define what handlers are available. Machines have fixed IOs, lists are somewhat the wrong thing...
public class InventoryRecipe extends InventoryBasic {

	public ArrayList<InventoryPieceItemHandler> itemInputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceFluidTank> fluidInputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceItemHandler> itemOutputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceFluidTank> fluidOutputPieces = new ArrayList<>();
	// TODO
	public InventoryPieceFluidTank steamPiece;
	public InventoryPieceItemHandler fuelHandlerPiece;
	public InventoryPieceProgressBar progressBar;

	public InventoryRecipe(IHasInventory<InventoryRecipe> parent) {
		super(parent);
	}

	public InventoryRecipe addItemInput(String name, int[] xPos, int[] yPos) {
		if(xPos.length != yPos.length) {
			throw new RuntimeException("Your inventory position array sizes do not match");
		}
		InventoryPieceItemHandler iPiece = new InventoryPieceItemHandler(name, this, IOType.INPUT,
				new ItemStackHandlerSmart(xPos.length, this), xPos, yPos);
		itemInputPieces.add(iPiece);
		itemPieces.put(name, iPiece);
		return this;
	}

	public InventoryRecipe addItemOutput(String name, int[] xPos, int[] yPos) {
		if(xPos.length != yPos.length) {
			throw new RuntimeException("Your inventory position array sizes do not match");
		}
		InventoryPieceItemHandler iPiece = new InventoryPieceItemHandler(name, this, IOType.OUTPUT,
				new ItemStackHandlerSmart(xPos.length, this), xPos, yPos);
		itemOutputPieces.add(iPiece);
		itemPieces.put(name, iPiece);
		return this;
	}

	public InventoryRecipe addFluidInput(String name, int xPos, int yPos, int capacity) {
		InventoryPieceFluidTank fPiece = new InventoryPieceFluidTank(name, this, IOType.INPUT,
				new FluidTankSmart(capacity, this), xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.put(name, fPiece);
		return this;
	}

	public InventoryRecipe addFluidOutput(String name, int xPos, int yPos, int capacity) {
		InventoryPieceFluidTank fPiece = new InventoryPieceFluidTank(name, this, IOType.OUTPUT,
				new FluidTankSmart(capacity, this), xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.put(name, fPiece);
		return this;
	}

	public InventoryRecipe setSteamTank(int xPos, int yPos) {
		return this.setSteamTank(xPos, yPos, Fluid.BUCKET_VOLUME * 16);
	}

	public InventoryRecipe setSteamTank(int xPos, int yPos, int capacity) {
		steamPiece = new InventoryPieceFluidTank("steamTank", this, IOType.POWER,
				new FluidTankSingleSmart(capacity, "steam", this), xPos, yPos);
		fluidPieces.put("steamTank", steamPiece);
		return this;
	}

	// TODO
	@Deprecated
	public InventoryRecipe setFuelHandler(int xPos, int yPos, ItemStackHandlerFuel handler) {
		fuelHandlerPiece = new InventoryPieceItemHandler("fuel", this, IOType.POWER, handler, new int[] { xPos },
				new int[] { yPos });
		itemPieces.put("fuel", fuelHandlerPiece);
		return this;
	}

	public InventoryRecipe setProgressBar(int x, int y) {
		this.progressBar = new InventoryPieceProgressBar(this, x, y);
		return this;
	}

	// TODO
	public InventoryRecipe addFluidInput(String name, int xPos, int yPos, FluidTankSingleSmart fluidTankSingleSmart) {
		InventoryPieceFluidTank fPiece = new InventoryPieceFluidTank(name, this, IOType.INPUT, fluidTankSingleSmart,
				xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.put(name, fPiece);
		return this;
	}

}
