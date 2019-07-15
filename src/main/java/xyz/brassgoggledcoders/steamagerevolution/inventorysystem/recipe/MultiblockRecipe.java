package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.MultiblockInventory;

public abstract class MultiblockRecipe<I extends InventoryRecipe> extends MultiblockInventory<I> {

	protected MultiblockRecipe(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {
		onTick();
		return this.getInventory().updateServer();
	}

	@Override
	protected void updateClient() {
		this.getInventory().updateClient();
	}

	protected void onTick() {
		// NO-OP
	}

}
