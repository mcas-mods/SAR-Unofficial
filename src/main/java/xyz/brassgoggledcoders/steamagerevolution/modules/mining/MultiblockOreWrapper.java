package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import net.minecraft.nbt.NBTTagCompound;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolid;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHolder;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventorySemisolid;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public class MultiblockOreWrapper implements ISemisolidHandler {

	final SARMultiblockTileInventory<SARMultiblockInventory<InventorySemisolid>> tile;

	@SuppressWarnings("unchecked")
	public MultiblockOreWrapper(SARMultiblockTileInventory<?> tile) {
		this.tile = (SARMultiblockTileInventory<SARMultiblockInventory<InventorySemisolid>>) tile;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		// NO-OP
		return new NBTTagCompound();
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		// NO-OP;
	}

	@Override
	public ISemisolidHolder[] getHolders() {
		if (tile.isConnected() && tile.getMultiblockController().isAssembled()) {
			return tile.getMultiblockController().getInventory().ore.getHandler().getHolders();
		}
		return new ISemisolidHolder[0];
	}

	@Override
	public boolean fill(SemisolidStack material) {
		if (tile.isConnected() && tile.getMultiblockController().isAssembled()) {
			return tile.getMultiblockController().getInventory().ore.getHandler().fill(material);
		}
		return false;
	}

	@Override
	public boolean drain(ISemisolid type, int toDrain) {
		if (tile.isConnected() && tile.getMultiblockController().isAssembled()) {
			return tile.getMultiblockController().getInventory().ore.getHandler().drain(type, toDrain);
		}
		return false;
	}

}
