package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.ArrayList;

import net.minecraftforge.fluids.Fluid;
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

	public InventoryRecipe(IHasInventory parent) {
		super(parent);
	}

	public InventoryRecipe addItemInput(String name, int[] xPos, int[] yPos) {
		if(xPos.length != yPos.length) {
			throw new RuntimeException("Your inventory position array sizes do not match");
		}
		InventoryPieceTypedHandler<ItemStackHandlerSmart> iPiece = new InventoryPieceTypedHandler<ItemStackHandlerSmart>(
				name, IOType.INPUT, new ItemStackHandlerSmart(xPos.length, parent), xPos, yPos);
		itemInputPieces.add(iPiece);
		itemPieces.put(name, iPiece);
		return this;
	}

	public InventoryRecipe addItemOutput(String name, int[] xPos, int[] yPos) {
		if(xPos.length != yPos.length) {
			throw new RuntimeException("Your inventory position array sizes do not match");
		}
		InventoryPieceTypedHandler<ItemStackHandlerSmart> iPiece = new InventoryPieceTypedHandler<ItemStackHandlerSmart>(
				name, IOType.OUTPUT, new ItemStackHandlerSmart(xPos.length, parent), xPos, yPos);
		itemOutputPieces.add(iPiece);
		itemPieces.put(name, iPiece);
		return this;
	}

	public InventoryRecipe addFluidInput(String name, int xPos, int yPos, int capacity) {
		InventoryPieceTypedHandler<FluidTankSmart> fPiece = new InventoryPieceTypedHandler<FluidTankSmart>(name,
				IOType.INPUT, new FluidTankSmart(capacity, parent), xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.put(name, fPiece);
		return this;
	}

	public InventoryRecipe addFluidOutput(String name, int xPos, int yPos, int capacity) {
		InventoryPieceTypedHandler<FluidTankSmart> fPiece = new InventoryPieceTypedHandler<FluidTankSmart>(name,
				IOType.OUTPUT, new FluidTankSmart(capacity, parent), xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.put(name, fPiece);
		return this;
	}

	public InventoryRecipe setSteamTank(int xPos, int yPos) {
		return this.setSteamTank(xPos, yPos, Fluid.BUCKET_VOLUME * 16);
	}

	public InventoryRecipe setSteamTank(int xPos, int yPos, int capacity) {
		steamPiece = new InventoryPieceTypedHandler<FluidTankSingleSmart>("steamTank", IOType.POWER,
				new FluidTankSingleSmart(capacity, "steam", parent, IOType.POWER), xPos, yPos);
		fluidPieces.put("steamTank", steamPiece);
		return this;
	}

	// TODO
	@Deprecated
	public InventoryRecipe setFuelHandler(int xPos, int yPos, ItemStackHandlerFuel handler) {
		fuelHandlerPiece = new InventoryPieceTypedHandler<ItemStackHandlerFuel>("fuel", IOType.POWER, handler, xPos,
				yPos);
		itemPieces.put("fuel", fuelHandlerPiece);
		return this;
	}

	// TODO
	@Override
	@Deprecated
	public InventoryRecipe setProgressBar(InventoryPieceProgressBar bar) {
		super.setProgressBar(bar);
		return this;
	}

	// TODO
	public InventoryRecipe addFluidInput(String name, int xPos, int yPos, FluidTankSingleSmart fluidTankSingleSmart) {
		InventoryPieceTypedHandler<FluidTankSmart> fPiece = new InventoryPieceTypedHandler<FluidTankSmart>(name,
				IOType.INPUT, fluidTankSingleSmart, xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.put(name, fPiece);
		return this;
	}

}
