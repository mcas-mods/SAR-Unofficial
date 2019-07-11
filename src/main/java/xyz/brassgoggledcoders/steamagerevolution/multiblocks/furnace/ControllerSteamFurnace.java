package xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace;

import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.SARMultiblockRecipe;

public class ControllerSteamFurnace extends SARMultiblockRecipe<InventoryRecipe> {

	public ControllerSteamFurnace(World world) {
		super(world);
		setInventory(new InventoryRecipe(this).addItemHandler("input", IOType.INPUT, new int[] { 48 }, new int[] { 33 })
				.addItemHandler("output", IOType.OUTPUT, new int[] { 108 }, new int[] { 33 })
				.setSteamTank(13, 9, Fluid.BUCKET_VOLUME * 16).setProgressBar(72, 33));
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
