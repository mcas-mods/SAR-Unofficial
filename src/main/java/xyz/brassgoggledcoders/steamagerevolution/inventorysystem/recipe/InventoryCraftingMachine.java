package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import java.util.*;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.teamacronymcoders.base.util.inventory.IngredientFluidStack;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.PacketSetRecipeTime;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.PacketStatusUpdate;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;

//TODO Drain totalSteam/ticksToComplete steam every tick
public class InventoryCraftingMachine extends InventoryBasic {

	public HashMap<IOType, ArrayList<ItemStackHandlerSync>> itemIOs = new HashMap<>();
	public HashMap<IOType, ArrayList<FluidTankSync>> fluidIOs = new HashMap<>();

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

	public MachineRecipe getCurrentRecipe() {
		return currentRecipe;
	}

	public void setCurrentRecipe(@Nullable MachineRecipe recipe) {
		if(recipe != null) {
			this.currentRecipe = recipe;
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketSetRecipeTime(this.enclosingMachine.getMachinePos(),
							Integer.valueOf(this.currentRecipe.ticksToProcess).shortValue()),
					this.enclosingMachine.getMachinePos(),
					this.enclosingMachine.getMachineWorld().provider.getDimension());
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
		if(!this.enclosingMachine.getMachineWorld().isRemote) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketStatusUpdate(this.enclosingMachine.getMachinePos(), currentProgress, error.networkID),
					this.enclosingMachine.getMachinePos(),
					this.enclosingMachine.getMachineWorld().provider.getDimension());
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
		if(this.getTypedFluidHandlers(IOType.POWER).get(0) != null) {
			if(this.getTypedFluidHandlers(IOType.POWER).get(0).getFluidAmount() >= currentRecipe
					.getSteamUsePerCraft()) {
				this.getTypedFluidHandlers(IOType.POWER).get(0).drain(currentRecipe.getSteamUsePerCraft(), true);
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
		enclosingMachine.markMachineDirty();
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
			if(this.getTypedFluidHandlers(IOType.POWER).isEmpty() || this.getTypedFluidHandlers(IOType.POWER).get(0)
					/* TODO */.getFluidAmount() >= currentRecipe.getSteamUsePerCraft()) {
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
			Optional<MachineRecipe> recipe = RecipeRegistry.getRecipesForMachine(this.enclosingMachine.getUID())
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

	public ArrayList<ItemStackHandlerSync> getTypedItemHandlers(IOType type) {
		return this.itemIOs.get(type);
	}

	public ArrayList<FluidTankSync> getTypedFluidHandlers(IOType type) {
		return this.fluidIOs.get(type);
	}

	@Override
	public void createSublists() {
		super.createSublists();
		for(IOType type : IOType.values()) {
			itemIOs.put(type, new ArrayList<>());
			fluidIOs.put(type, new ArrayList<>());
		}
		this.getInventoryPiecesOfType(InventoryPieceItemHandler.class).stream()
				.filter(piece -> piece.getType() != null)
				.forEach(piece -> this.itemIOs.get(piece.getType()).add(piece.getHandler()));
		this.getInventoryPiecesOfType(InventoryPieceFluidTank.class).stream()
				.filter(piece -> piece.getType() != null)
				.forEach(piece -> this.fluidIOs.get(piece.getType()).add(piece.getHandler()));
	}

	@Nonnull
	public RecipeError getRecipeError() {
		return this.currrentError;
	}
}
