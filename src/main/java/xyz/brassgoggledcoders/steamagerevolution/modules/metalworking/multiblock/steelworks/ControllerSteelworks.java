package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks;

import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerSteelworks extends SARMultiblockInventory {

	public ControllerSteelworks(World world) {
		super(world);
		this.setInventory(new InventoryMachine(new InventoryPieceItem(new ItemStackHandlerFuel(1, this), 83, 31),
				new InventoryPieceFluid(new MultiFluidTank(Fluid.BUCKET_VOLUME * 16, this, 1), 41, 9), null,
				new InventoryPieceFluid(new MultiFluidTank(Fluid.BUCKET_VOLUME * 16, this, 1), 141, 9),
				new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this), 10, 9))
						.setProgressBar(new InventoryPieceProgressBar(110, 31)));
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 97;
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
		return 9;
	}

	@Override
	public String getName() {
		return "Steelworks";
	}

	@Override
	public int getMaximumXSize() {
		return getMinimumXSize();
	}

	@Override
	public int getMaximumZSize() {
		return getMinimumZSize();
	}

	@Override
	public int getMaximumYSize() {
		return getMinimumYSize();
	}
}
