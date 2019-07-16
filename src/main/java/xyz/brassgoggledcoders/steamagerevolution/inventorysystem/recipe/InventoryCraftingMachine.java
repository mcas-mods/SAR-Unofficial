package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.teamacronymcoders.base.util.inventory.IngredientFluidStack;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.PacketSetRecipeTime;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.PacketStatusUpdate;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;

//TODO Drain totalSteam/ticksToComplete steam every tick
public class InventoryCraftingMachine extends InventoryBasic {

	public ArrayList<InventoryPieceItemHandler> itemInputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceFluidTank> fluidInputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceItemHandler> itemOutputPieces = new ArrayList<>();
	public ArrayList<InventoryPieceFluidTank> fluidOutputPieces = new ArrayList<>();
	// TODO
	public InventoryPieceFluidTank steamPiece;
	public InventoryPieceItemHandler fuelHandlerPiece;

	@SideOnly(Side.CLIENT)
	public int clientTicksToComplete;
	// FIXME remember this needs to be saved to NBT
	private int currentProgress = 0;
	protected MachineRecipe currentRecipe;

	@Nonnull
	RecipeError currrentError = RecipeError.NONE;

	public InventoryCraftingMachine(IHasInventory<? extends InventoryCraftingMachine> parent) {
		super(parent);
	}

	public InventoryCraftingMachine addItemHandler(String name, IOType type, int xPos, int yPos) {
		this.addItemHandler(name, type, new int[] { xPos }, new int[] { yPos });
		return this;
	}

	public InventoryCraftingMachine addItemHandler(String name, IOType type, int[] slotXs, int[] slotYs) {
		if(slotXs.length != slotYs.length) {
			throw new RuntimeException("Your inventory position array sizes do not match");
		}
		new InventoryPieceItemHandler(name, this, type, new ItemStackHandlerSync(name, slotXs.length, parent), slotXs,
				slotYs);
		// itemPieces.put(name, iPiece);
		return this;
	}

	public InventoryCraftingMachine addFluidHandler(String name, IOType type, int xPos, int yPos, int capacity) {
		new InventoryPieceFluidTank(name, this, type, new FluidTankSync(name, capacity, parent), xPos, yPos);
		return this;
	}

	public InventoryCraftingMachine setSteamTank(int xPos, int yPos) {
		return this.setSteamTank(xPos, yPos, Fluid.BUCKET_VOLUME * 16);
	}

	public InventoryCraftingMachine setSteamTank(int xPos, int yPos, int capacity) {
		steamPiece = new InventoryPieceFluidTank("steamTank", this, IOType.POWER,
				new FluidTankSingleSync("steamTank", capacity, "steam", parent), xPos, yPos);
		// fluidPieces.put("steamTank", steamPiece);
		return this;
	}

	// TODO
	@Deprecated
	public InventoryCraftingMachine setFuelHandler(int xPos, int yPos, ItemStackHandlerFuel handler) {
		fuelHandlerPiece = new InventoryPieceItemHandler("fuel", this, IOType.POWER, handler, new int[] { xPos },
				new int[] { yPos });
		// itemPieces.put("fuel", fuelHandlerPiece);
		return this;
	}

	public InventoryCraftingMachine setProgressBar(int x, int y) {
		new InventoryPieceProgressBar(this, x, y);
		return this;
	}

	// TODO
	@Deprecated
	public InventoryCraftingMachine addFluidInput(String name, int xPos, int yPos,
			FluidTankSingleSync fluidTankSingleSmart) {
		new InventoryPieceFluidTank(name, this, IOType.INPUT, fluidTankSingleSmart, xPos, yPos);
		// fluidInputPieces.add(fPiece);
		// fluidPieces.put(name, fPiece);
		return this;
	}

	public MachineRecipe getCurrentRecipe() {
		return currentRecipe;
	}

	public void setCurrentRecipe(@Nullable MachineRecipe recipe) {
		if(recipe != null) {
			this.currentRecipe = recipe;
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketSetRecipeTime(this.parent.getMachinePos(),
							Integer.valueOf(this.currentRecipe.ticksToProcess).shortValue()),
					this.parent.getMachinePos(), this.parent.getMachineWorld().provider.getDimension());
		}
		else {
			this.currentRecipe = null;
			this.currentProgress = 0;
		}
	}

	public int getCurrentTicks() {
		return currentProgress;
	}

	@SideOnly(Side.CLIENT)
	public int getMaxTicks() {
		return clientTicksToComplete;
	}

	public void setCurrentTicks(int ticks) {
		this.currentProgress = ticks;
	}

	public boolean updateServer() {
		if(canRun()) {
			this.setRecipeError(RecipeError.NONE);
			if(getCurrentTicks() <= currentRecipe.getTicksPerOperation()) { // TODO
				setCurrentTicks(getCurrentTicks() + 1);
			}
			if(canFinish()) {
				onFinish();
				this.setCurrentRecipe(null);// TODO Only do if items have changed an that
				return true;
			}
		}
		else {
			this.setCurrentRecipe(null);
		}
		return false;
	}

	public void setRecipeError(RecipeError error) {
		if(!this.parent.getMachineWorld().isRemote) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketStatusUpdate(this.parent.getMachinePos(), currentProgress, error.networkID),
					this.parent.getMachinePos(), this.parent.getMachineWorld().provider.getDimension());
		}
		this.currrentError = error;
	}

	// Interpolate ticks on client TODO Running whenever a recipe is present doesn't
	// work, needs to be only when the machine can run
	public void updateClient() {
		if(this.clientTicksToComplete > 0) {
			if(this.currentProgress < this.clientTicksToComplete) {
				this.currentProgress++;
			}
			else {
				this.currentProgress = 0;
			}
		}
	}

	protected void onFinish() {
		boolean extractedItems = true;
		boolean extractedFluids = true;
		boolean extractedSteam = true;
		if(ArrayUtils.isNotEmpty(currentRecipe.getItemInputs())) {
			// TODO Good lord loops
			int matched = 0;
			for(Ingredient input : currentRecipe.getItemInputs()) {
				for(ItemStackHandler handler : getTypedItemHandlers(IOType.INPUT)) {
					for(int i = 0; i < handler.getSlots(); i++) {
						if(input.apply(handler.getStackInSlot(i))) {
							handler.extractItem(i, 1, false);
							matched++;
						}
					}
				}
			}
			extractedItems = (currentRecipe.getItemInputs().length == matched);
		}
		if(ArrayUtils.isNotEmpty(currentRecipe.getFluidInputs())) {
			for(IngredientFluidStack input : currentRecipe.getFluidInputs()) {
				for(FluidTank tank : getTypedFluidHandlers(IOType.INPUT)) {
					if(tank.drain(input.getFluid(), false) != null
							&& tank.drain(input.getFluid(), false).amount == input.getFluid().amount) {
						tank.drain(input.getFluid(), true);
					}
					else {
						extractedFluids = false;
					}
				}
			}
		}
		if(steamPiece != null) {
			if(steamPiece.getHandler().getFluidAmount() >= currentRecipe.getSteamUsePerCraft()) {
				steamPiece.getHandler().drain(currentRecipe.getSteamUsePerCraft(), true);
			}
			else {
				extractedSteam = false;
			}
		}
		if(extractedItems && extractedFluids && extractedSteam) {
			if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
				for(ItemStack output : currentRecipe.getItemOutputs().clone()) {
					for(ItemStackHandler handler : getTypedItemHandlers(IOType.OUTPUT)) {
						ItemHandlerHelper.insertItem(handler, output.copy(), false);
					}
				}
			}
			if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
				for(FluidStack output : currentRecipe.getFluidOutputs().clone()) {
					for(FluidTank tank : getTypedFluidHandlers(IOType.OUTPUT)) {
						tank.fill(output.copy(), true);
					}
				}
			}
		}
		else {
			SteamAgeRevolution.instance.getLogger()
					.info("Machine encountered recipe error at final stage. This should not happen..." + extractedItems
							+ "/" + extractedFluids + "/" + extractedSteam);
		}
		parent.markMachineDirty();
	}

	protected boolean canFinish() {
		if(currentRecipe != null && currentProgress >= currentRecipe.getTicksPerOperation()) {
			boolean roomForItems = true;
			boolean roomForFluids = true;
			if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
				roomForItems = Arrays.asList(currentRecipe.getItemOutputs()).parallelStream()
						.allMatch(output -> getTypedItemHandlers(IOType.OUTPUT).stream()
								.anyMatch(h -> ItemHandlerHelper.insertItem(h, output, true).isEmpty()));
			}
			if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
				roomForFluids = Arrays.asList(currentRecipe.getFluidOutputs()).parallelStream()
						.allMatch(output -> getTypedFluidHandlers(IOType.OUTPUT).stream()
								.anyMatch(t -> t.fill(output, false) == output.amount));
			}
			if(!roomForItems || !roomForFluids) {
				this.setRecipeError(RecipeError.OUTPUT_BLOCKED);
			}
			return roomForItems && roomForFluids;
		}
		return false;
	}

	protected boolean canRun() {
		// If we already have a recipe, check we have enough steam to continue
		if(currentRecipe != null) {
			if(steamPiece == null || steamPiece.getHandler().getFluidAmount() >= currentRecipe.getSteamUsePerCraft()) {
				return true;
			}
			else {
				this.setRecipeError(RecipeError.INSUFFICIENT_STEAM);
				return false;
			}
		}
		// Otherwise, try to find a recipe from the current inputs
		else {
			// TODO Sort recipes by size of input
			Optional<MachineRecipe> recipe = RecipeRegistry.getRecipesForMachine(this.parent.getName().toLowerCase())
					.parallelStream().filter(r -> hasRequiredFluids(r)).filter(r -> hasRequiredItems(r)).findFirst();
			if(recipe.isPresent()) {
				setCurrentRecipe(recipe.get());
				return true;
			}
		}
		return false;
	}

	public boolean hasRequiredFluids(MachineRecipe recipe) {
		// Check if the recipe has any fluid inputs required
		if(ArrayUtils.isNotEmpty(recipe.getFluidInputs())) {
			// Stream the fluid stacks required
			return Arrays.stream(recipe.getFluidInputs())
					// Apply tanksHaveFluid to each element and output result to stream
					.map(stack -> tanksHaveFluid(stack))
					// Reduce list of booleans into one - so will only evaluate true if every
					// boolean is true
					.reduce((a, b) -> a && b).orElse(false);
		}
		// Else just return true
		return true;
	}

	private boolean tanksHaveFluid(IngredientFluidStack stack) {
		return getTypedFluidHandlers(IOType.INPUT).stream().filter(Objects::nonNull)
				.filter(tank -> tank.getFluidAmount() > 0)
				.filter(tank -> tank.getFluid().containsFluid(stack.getFluid())).findAny().isPresent();
	}

	public boolean hasRequiredItems(MachineRecipe recipe) {
		if(ArrayUtils.isNotEmpty(recipe.getItemInputs())) {
			return Arrays.stream(recipe.getItemInputs()).map(ing -> handlerHasItems(ing)).reduce((a, b) -> a && b)
					.orElse(false);
		}
		return true;
	}

	private boolean handlerHasItems(Ingredient ingredient) {
		return this.getTypedItemHandlers(IOType.INPUT).stream()
				.filter(handler -> IntStream.range(0, handler.getSlots())
						.mapToObj(slotNum -> handler.getStackInSlot(slotNum))
						.filter(inputStack -> ingredient.apply(inputStack)).findAny().isPresent())
				.findAny().isPresent();
	}

	// TODO
	@Deprecated
	public List<ItemStackHandler> getTypedItemHandlers(IOType type) {
		return itemPieces.values().stream().filter(iP -> iP.getType().equals(type)).map(p -> p.getHandler())
				.collect(Collectors.toList());
	}

	// TODO
	@Deprecated
	public List<FluidTank> getTypedFluidHandlers(IOType type) {
		return fluidPieces.values().stream().filter(iP -> iP.getType().equals(type)).map(p -> p.getHandler())
				.collect(Collectors.toList());
	}

	@Nonnull
	public RecipeError getRecipeError() {
		return this.currrentError;
	}
}
