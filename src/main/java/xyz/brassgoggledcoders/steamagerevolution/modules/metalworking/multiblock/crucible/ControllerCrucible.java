package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.PositionUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.InventoryMachine.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.InventoryMachine.InventoryPieceItem;

public class ControllerCrucible extends SARMultiblockInventory {

	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;

	public ControllerCrucible(World world) {
		super(world);
		this.setInventory(new InventoryMachine(new InventoryPieceItem(new ItemStackHandlerExtractSpecific(1), 53, 33),
				null, null, new InventoryPieceFluid(new MultiFluidTank(Fluid.BUCKET_VOLUME * 4, this, 0, 1), 85, 10),
				new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME, "steam", this), 0, 0)));
	}

	// FIXME Caching
	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions = PositionUtils.shrinkPositionCubeBy(getMinimumCoord(),
				getMaximumCoord(), 1);
		minimumInteriorPos = interiorPositions.getLeft();
		maximumInteriorPos = interiorPositions.getRight();

		int blocksInside = 0;
		// TODO Expensive for loop just to increment an integer
		for(BlockPos pos : BlockPos.getAllInBoxMutable(minimumInteriorPos, maximumInteriorPos)) {
			blocksInside++;
		}
		// Size internal tank accordingly
		MultiFluidTank newTank = new MultiFluidTank(blocksInside * Fluid.BUCKET_VOLUME * 16, this, 0, 1);
		if(this.inventory.getOutputTank().fluids != null) {
			newTank.fluids.addAll(this.inventory.getOutputTank().fluids);
		}
		this.inventory.setFluidOutput(newTank);
		super.onMachineAssembled();
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 17;
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
