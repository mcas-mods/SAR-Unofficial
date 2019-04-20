package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.ICrushedHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.ICrushedHolder;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill.ControllerDrill;

public class MultiblockOreWrapper implements ICrushedHandler {

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
	public ICrushedHolder[] getHolders() {
		if(tile.isConnected()) {
			tile.getMultiblockController().getInventory().ore.getHandler().getHolders();
		}
		return null;
	}

}
