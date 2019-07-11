package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder;

import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockRecipe;

public class ControllerGrinder extends MultiblockRecipe<InventoryRecipe> {

	public ControllerGrinder(World world) {
		super(world);
		this.setInventory(
				new InventoryRecipe(this).addItemHandler("itemInput", IOType.INPUT, new int[] { 58 }, new int[] { 32 })
						.addItemHandler("itemOutput", IOType.OUTPUT, new int[] { 121 }, new int[] { 32 })
						.setSteamTank(10, 9).setProgressBar(87, 33));
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
