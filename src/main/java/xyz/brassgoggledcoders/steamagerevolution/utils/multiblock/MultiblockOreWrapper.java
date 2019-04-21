package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import net.minecraft.nbt.NBTTagCompound;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidStack;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHolder;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill.ControllerDrill;

public class MultiblockOreWrapper implements ISemisolidHandler {

	final SARMultiblockTileBase<ControllerDrill> tile;

	public MultiblockOreWrapper(SARMultiblockTileBase<ControllerDrill> tile) {
		this.tile = tile;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		if(tile.isConnected()) {
			return tile.getMultiblockController().getInventory().ore.getHandler().serializeNBT();
		}
		return new NBTTagCompound();
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		if(tile.isConnected()) {
			tile.getMultiblockController().getInventory().ore.getHandler().deserializeNBT(tag);
		}
	}

	@Override
	public ISemisolidHolder[] getHolders() {
		if(tile.isConnected()) {
			tile.getMultiblockController().getInventory().ore.getHandler().getHolders();
		}
		return new ISemisolidHolder[0];
	}

	@Override
	public boolean fill(SemisolidStack material) {
		if(tile.isConnected()) {
			tile.getMultiblockController().getInventory().ore.getHandler().fill(material);
		}
		return false;
	}

	@Override
	public boolean drain(ISemisolid type, int toDrain) {
		if(tile.isConnected()) {
			tile.getMultiblockController().getInventory().ore.getHandler().drain(type, toDrain);
		}
		return false;
	}

}
