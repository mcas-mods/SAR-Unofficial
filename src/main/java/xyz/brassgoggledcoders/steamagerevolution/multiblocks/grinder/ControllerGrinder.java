package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder;

import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.SARMultiblockRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceProgressBar;

public class ControllerGrinder extends SARMultiblockRecipe<InventoryRecipe> {

	protected ControllerGrinder(World world) {
		super(world);
		this.setInventory(new InventoryRecipe(this).addItemInput("itemInput", new int[] { 58 }, new int[] { 32 })
				.addItemOutput("itemOutput", new int[] { 121 }, new int[] { 32 }).setSteamTank(10, 9)
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
