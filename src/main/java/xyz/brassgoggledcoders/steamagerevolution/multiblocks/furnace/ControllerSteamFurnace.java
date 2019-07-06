package xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace;

import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerSteamFurnace extends SARMultiblockInventory<InventoryBasic> {

	public ControllerSteamFurnace(World world) {
		super(world);
		setInventory(new InventoryBasic(new InventoryPieceItem(new ItemStackHandlerSmart(1, this), 48, 33), null,
				new InventoryPieceItem(new ItemStackHandlerSmart(1, this), 108, 33), null,
				new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this), 13, 9)).setProgressBar(new InventoryPieceProgressBar(72, 33)));
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;
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
		return 6;
	}

	@Override
	public int getMaximumZSize() {
		return 6;
	}

	@Override
	public int getMaximumYSize() {
		return 6;
	}

	@Override
	public String getName() {
		return "Steam Furnace";
	}
}
