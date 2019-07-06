package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerCrucible extends SARMultiblockInventory<InventoryRecipe> {

	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;

	public ControllerCrucible(World world) {
		super(world);
		setInventory(new InventoryRecipe()
				.addItemInput("itemInput", new int[] { 53 }, new int[] { 34 },
						new ItemStackHandlerSmart(1, this, IOType.INPUT))
				.addFluidOutput("output", 105, 11, new FluidTankSmart(Fluid.BUCKET_VOLUME, this))
				.setSteamTank(17, 11, Fluid.BUCKET_VOLUME, this).setProgressBar(new InventoryPieceProgressBar(76, 33)));
	}

	// FIXME Caching
	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions = com.teamacronymcoders.base.util.PositionUtils
				.shrinkPositionCubeBy(getMinimumCoord(), getMaximumCoord(), 1);
		minimumInteriorPos = interiorPositions.getLeft();
		maximumInteriorPos = interiorPositions.getRight();

		int blocksInside = 0;
		// TODO Expensive for loop just to increment an integer
		for(BlockPos pos : BlockPos.getAllInBoxMutable(minimumInteriorPos, maximumInteriorPos)) {
			blocksInside++;
		}
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
