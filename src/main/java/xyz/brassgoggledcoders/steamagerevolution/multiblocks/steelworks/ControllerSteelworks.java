package xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks;

import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.SARMultiblockRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceProgressBar;

public class ControllerSteelworks extends SARMultiblockRecipe<InventoryRecipe> {

	public ControllerSteelworks(World world) {
		super(world);
		setInventory(new InventoryRecipe(this).addItemInput("itemInput", new int[] { 83 }, new int[] { 31 })
				.addFluidInput("ironTank", 41, 9, Fluid.BUCKET_VOLUME * 16)
				.addFluidOutput("steelTank", 141, 9, Fluid.BUCKET_VOLUME * 16).setSteamTank(10, 9)
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
