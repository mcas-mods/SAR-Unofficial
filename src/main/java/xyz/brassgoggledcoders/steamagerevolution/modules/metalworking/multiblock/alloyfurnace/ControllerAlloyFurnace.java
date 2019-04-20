package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class ControllerAlloyFurnace extends SARMultiblockInventory {

	public static int inputCapacity = RecipeUtil.VALUE_BLOCK * 8;
	public static int outputCapacity = Fluid.BUCKET_VOLUME * 8;

	public ControllerAlloyFurnace(World world) {
		super(world);
		setInventory(new InventoryMachine(
				new InventoryPieceFluid(new MultiFluidTank(inputCapacity, this, 2), new int[] { 22, 78 },
						new int[] { 11, 11 }),
				new InventoryPieceFluid(new MultiFluidTank(outputCapacity, this, 1), 134, 17), null));
	}

	// @Override
	// protected FluidTank getTank(String toWrap) {
	// if(toWrap.equalsIgnoreCase("input")) {
	// return fluidInput;
	// }
	// return outputTank;
	// }

	@Override
	protected boolean canRun() {
		return inventory.getInputTank().fluids.size() == 2 && super.canRun();
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
		return "Alloy Forge";
	}
}
