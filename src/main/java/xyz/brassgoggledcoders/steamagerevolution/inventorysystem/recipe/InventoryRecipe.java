package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
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

	@SideOnly(Side.CLIENT) // TODO I forgot why this is a thing
	public int currentMaxTicks;
	// FIXME remember this needs to be saved
	private int currentTicks = 0;
	protected SARMachineRecipe currentRecipe;

	public InventoryRecipe(IHasInventory<? extends InventoryRecipe> parent) {
		super(parent);
	}

	public InventoryRecipe addItemHandler(String name, IOType type, int xPos, int yPos) {
		this.addItemHandler(name, type, new int[] { xPos }, new int[] { yPos });
		return this;
	}

	public InventoryRecipe addItemHandler(String name, IOType type, int[] slotXs, int[] slotYs) {
		if(slotXs.length != slotYs.length) {
			throw new RuntimeException("Your inventory position array sizes do not match");
		}
		InventoryPieceItemHandler iPiece = new InventoryPieceItemHandler(name, this, type,
				new ItemStackHandlerSmart(slotXs.length, this), slotXs, slotYs);
		if(type.equals(IOType.INPUT)) {
			itemInputPieces.add(iPiece);
		}
		else if(type.equals(IOType.OUTPUT)) {
			itemOutputPieces.add(iPiece);
		}
		itemPieces.put(name, iPiece);
		return this;
	}

	public InventoryRecipe addFluidHandler(String name, IOType type, int xPos, int yPos, int capacity) {
		InventoryPieceFluidTank fPiece = new InventoryPieceFluidTank(name, this, type,
				new FluidTankSmart(capacity, this), xPos, yPos);
		if(type.equals(IOType.INPUT)) {
			fluidInputPieces.add(fPiece);
		}
		else if(type.equals(IOType.OUTPUT)) {
			fluidInputPieces.add(fPiece);
		}
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
	@Deprecated
	public InventoryRecipe addFluidInput(String name, int xPos, int yPos, FluidTankSingleSmart fluidTankSingleSmart) {
		InventoryPieceFluidTank fPiece = new InventoryPieceFluidTank(name, this, IOType.INPUT, fluidTankSingleSmart,
				xPos, yPos);
		fluidInputPieces.add(fPiece);
		fluidPieces.put(name, fPiece);
		return this;
	}

	public SARMachineRecipe getCurrentRecipe() {
		return currentRecipe;
	}

	public void setCurrentRecipe(SARMachineRecipe recipe) {
		if(recipe == null) {
			this.setCurrentTicks(0);
		}
		this.currentRecipe = recipe;
	}

	public int getCurrentTicks() {
		return currentTicks;
	}

	public int getCurrentMaxTicks() {
		return currentMaxTicks;
	}

	public void setCurrentTicks(int ticks) {
		this.currentTicks = ticks;
	}

	protected boolean onTick() {
		if(canRun()) {
			if(getCurrentTicks() <= currentRecipe.getTicksPerOperation()) { // TODO
				setCurrentTicks(getCurrentTicks() + 1);
			}
			if(canFinish()) {
				onFinish();
				setCurrentTicks(0);
				currentRecipe = null;
			}
			return true;
		}
		return false;
	}

	protected void onFinish() {
		RecipeMachineHelper.onFinish(currentRecipe, this);
		parent.markMachineDirty();
	}

	protected boolean canFinish() {
		return RecipeMachineHelper.canFinish(getCurrentTicks(), currentRecipe, this);
	}

	protected boolean canRun() {
		if(currentRecipe != null) {
			// TODO Send this (much!) less often!
			parent.markMachineDirty();
		}
		return RecipeMachineHelper.canRun(parent.getMachineWorld(), parent.getMachinePos(), parent,
				parent.getName().toLowerCase()/* .replace(' ', '_')TODO */, currentRecipe, this);
	}

	// TODO
	public List<ItemStackHandler> getTypedItemHandlers(IOType type) {
		return itemPieces.values().stream().filter(iP -> iP.getType().equals(type)).map(p -> p.getHandler())
				.collect(Collectors.toList());
	}

	// TODO
	public List<FluidTank> getTypedFluidHandlers(IOType type) {
		return fluidPieces.values().stream().filter(iP -> iP.getType().equals(type)).map(p -> p.getHandler())
				.collect(Collectors.toList());
	}
}
