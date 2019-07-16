package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class ControllerCrucible extends MultiblockCraftingMachine<InventoryCraftingMachine> {

	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;

	public ControllerCrucible(World world) {
		super(world);
		setInventory(
				new InventoryCraftingMachine(this).addItemHandler("itemInput", IOType.INPUT, new int[] { 53 }, new int[] { 34 })
						.addFluidHandler("output", IOType.OUTPUT, 105, 11, RecipeUtil.VALUE_BLOCK * 4)
						.setSteamTank(17, 11, Fluid.BUCKET_VOLUME).setProgressBar(76, 33));
	}

	// FIXME Caching
	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions = com.teamacronymcoders.base.util.PositionUtils
				.shrinkPositionCubeBy(getMinimumCoord(), getMaximumCoord(), 1);
		minimumInteriorPos = interiorPositions.getLeft();
		maximumInteriorPos = interiorPositions.getRight();

		// int blocksInside = 0;
		// // TODO Expensive for loop just to increment an integer
		// for(BlockPos pos : BlockPos.getAllInBoxMutable(minimumInteriorPos,
		// maximumInteriorPos)) {
		// blocksInside++;
		// }
		// Size internal tank accordingly
		// TODO
		// MultiFluidHandler newTank = new MultiFluidHandler(blocksInside *
		// Fluid.BUCKET_VOLUME, this, 1);
		// if(inventory.getOutputFluidHandler().fluids != null) {
		// newTank.fluids.addAll(inventory.getOutputFluidHandler().fluids);
		// }
		// inventory.setFluidOutput(newTank);
		super.onMachineAssembled();
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 17;
	}

	@Override
	public int getMinimumXSize() {
		return 3;
	}

	@Override
	public int getMinimumZSize() {
		return 3;
	}

	@Override
	public int getMinimumYSize() {
		return 3;
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
		return 5;
	}

	@Override
	public String getName() {
		return "Crucible";
	}
}
