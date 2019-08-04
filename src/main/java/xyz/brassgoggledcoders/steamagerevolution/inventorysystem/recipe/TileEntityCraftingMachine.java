package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import net.minecraft.util.ITickable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.TileEntityInventory;

public abstract class TileEntityCraftingMachine<I extends InventoryCraftingMachine> extends TileEntityInventory<I> implements ITickable {
	@Override
	public void update() {
		if(this.getWorld().isRemote) {
			this.getInventory().updateClient();
		}
		else {
			this.getInventory().updateServer();
		}
	}
}
