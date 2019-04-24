package xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHolder;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.SemisolidRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.GuiSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventoryPieceSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventorySemisolid;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeMachineHelper;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeRegistry;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class ControllerGrinder extends SARMultiblockInventory<InventorySemisolid> {

	protected ControllerGrinder(World world) {
		super(world);
		this.setInventory(new InventorySemisolid(new InventoryPieceItem(new ItemStackHandlerSmart(1, this), 0, 0),
				new InventoryPieceItem(new ItemStackHandlerSmart(1, this), 0, 0),
				new InventoryPieceSemisolid(new SemisolidHandler(new SemisolidHolder(30)), 126, 15),
				new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this), 13, 9)));
	}
	
	@Override
	protected void onFinish() {
		if(this.getCurrentRecipe() instanceof SemisolidRecipe) {
			SemisolidRecipe r = (SemisolidRecipe) this.getCurrentRecipe();
			if(ArrayUtils.isNotEmpty(r.getSemisolidInputs())) {
				for(SemisolidStack stack : r.getSemisolidInputs()) {
					this.getInventory().ore.getHandler().drain(stack.getMaterial(), stack.amount);
				}
			}
		}
		super.onFinish();
	}

	@Override
	protected boolean canRun() {
		if(currentRecipe != null) {
			if(inventory.getSteamTank() == null
					|| inventory.getSteamTank().getFluidAmount() >= currentRecipe.getSteamUsePerCraft()) {
				return true;
			}
		}
		else {
			Optional<SARMachineRecipe> recipe = RecipeRegistry.getRecipesForMachine(this.getName()).parallelStream()
					/*.filter(r -> hasRequiredFluids(inventory, r))*/.filter(r -> RecipeMachineHelper.hasRequiredItems(inventory, r)).filter(r -> hasRequiredSemisolids(inventory, r))
					.findFirst();
			if(recipe.isPresent()) {
				this.setCurrentRecipe(recipe.get());
			}
		}
		return false;
	}
	
	private static boolean hasRequiredSemisolids(InventorySemisolid inventory, SARMachineRecipe recipe) {
		if(recipe instanceof SemisolidRecipe) {
			SemisolidRecipe r = (SemisolidRecipe) recipe;
			if(ArrayUtils.isNotEmpty(r.getSemisolidInputs())) {
				// Stream the fluid stacks
				return Arrays.stream(r.getSemisolidInputs())
						// Apply to each element and output result to stream
						.map(stack -> handlersHaveMaterial(inventory, stack))
						// Reduce list of booleans into one - so will only evaluate true if every
						// boolean is true
						.reduce((a, b) -> a && b).orElse(false);
			}
		}
		return true;
	}
	
	private static boolean handlersHaveMaterial(InventorySemisolid inventory, SemisolidStack stack) {
		return Arrays
				.asList(inventory.ore.getHandler().getHolders()).stream().filter(Objects::nonNull).map(holder -> holder.getCrushed()).anyMatch(sta -> sta.equals(stack));
	}

	@Override
	public String getName() {
		return "Grinder";
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;
	}

	@Override
	public int getMaximumXSize() {
		return 3;
	}

	@Override
	public int getMaximumZSize() {
		return 3;
	}

	@Override
	public int getMaximumYSize() {
		return 3;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiSemisolid(entityPlayer, this, "");
	}


}
