package xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class ControllerAlloyFurnace extends SARMultiblockInventory<InventoryRecipe> {

	public static int inputCapacity = RecipeUtil.VALUE_BLOCK * 8;
	public static int outputCapacity = Fluid.BUCKET_VOLUME * 8;

	public ControllerAlloyFurnace(World world) {
		super(world);
		setInventory(new InventoryRecipe().addFluidInput("one", 22, 11, new FluidTankSmart(inputCapacity, this))
				.addFluidInput("two", 78, 11, new FluidTankSmart(inputCapacity, this))
				.addFluidOutput("output", 134, 17, new FluidTankSmart(outputCapacity, this)));
	}

	@Override
	protected boolean canRun() {
		return this.getInventory().fluidInputPieces.get(0).getHandler().getFluidAmount() > 0
				&& this.getInventory().fluidInputPieces.get(1).getHandler().getFluidAmount() > 0 && super.canRun();
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO
		return 1;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		if(block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
			return true;
		}
		else {
			validatorCallback
					.setLastError(new ValidationError("steamagerevolution.multiblock.validation.alloyforgeinterior"));
			return false;
		}
	}

	@Override
	public int getMinimumXSize() {
		return 5;
	}

	@Override
	public int getMinimumZSize() {
		return 5;
	}

	@Override
	public int getMinimumYSize() {
		return 6;
	}

	@Override
	public int getMaximumXSize() {
		return 5;
	}

	@Override
	public int getMaximumZSize() {
		return 5;
	}

	@Override
	public int getMaximumYSize() {
		return 6;
	}

	@Override
	public String getName() {
		return "Alloy Furnace";
	}
}
