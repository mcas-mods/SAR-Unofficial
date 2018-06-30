package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.InventoryMachine.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.InventoryMachine.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.PositionUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerCrucible extends SARMultiblockInventory implements ISmartTankCallback {

	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;

	public static int steamPerOperation = Fluid.BUCKET_VOLUME / 10;

	public ControllerCrucible(World world) {
		super(world);
		this.setInventory(
				new InventoryMachine(new InventoryPieceItem(new ItemStackHandlerExtractSpecific(1), 0, 0), null, null,
						new InventoryPieceFluid(new MultiFluidTank(Fluid.BUCKET_VOLUME * 4, this, 0), 0, 0),
						new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME, "steam", this), 0, 0)));
	}

	// @Override
	// public ItemStackHandler getInventory(String toWrap) {
	// return solid;
	// }
	//
	// @Override
	// protected FluidTank getTank(String toWrap) {
	// if(toWrap.equals("output")) {
	// return tank;
	// }
	// return steamTank;
	// }

	// FIXME Caching
	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions = PositionUtils.shrinkPositionCubeBy(getMinimumCoord(),
				getMaximumCoord(), 1);
		minimumInteriorPos = interiorPositions.getLeft();
		maximumInteriorPos = interiorPositions.getRight();

		int blocksInside = 0;
		// TODO Expensive for loop just to increment an integer
		for(BlockPos pos : BlockPos.getAllInBox(minimumInteriorPos, maximumInteriorPos)) {
			blocksInside++;
		}
		// Size internal tank accordingly
		MultiFluidTank newTank = new MultiFluidTank(blocksInside * Fluid.BUCKET_VOLUME * 16, this, 0);
		newTank.fluids.addAll(this.inventory.getInputTank().fluids);
		this.inventory.setFluidInput(newTank);
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
