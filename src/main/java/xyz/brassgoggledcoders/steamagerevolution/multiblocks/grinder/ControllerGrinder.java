package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder;

import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryRecipeMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerGrinder extends SARMultiblockInventory<InventoryRecipeMachine> {

	protected ControllerGrinder(World world) {
		super(world);
		this.setInventory(new InventoryRecipeMachine(new InventoryPieceItem(new ItemStackHandlerSmart(1, this), 58, 32),
				null, new InventoryPieceItem(new ItemStackHandlerSmart(1, this), 121, 32), null,
				new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this), 10, 9))
						.setProgressBar(new InventoryPieceProgressBar(87, 33)));
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

}
