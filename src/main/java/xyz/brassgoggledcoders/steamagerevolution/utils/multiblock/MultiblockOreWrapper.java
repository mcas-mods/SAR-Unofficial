package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import xyz.brassgoggledcoders.steamagerevolution.api.IHeavyOreHolder;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill.ControllerDrill;

public class MultiblockOreWrapper implements IHeavyOreHolder {

	final SARMultiblockTileBase<ControllerDrill> tile;

	public MultiblockOreWrapper(SARMultiblockTileBase<ControllerDrill> tile) {
		this.tile = tile;
	}

	@Override
	public int getOreAmount(String oreName) {
		if(tile.isConnected()) {
			return tile.getMultiblockController().getInventory().ore.getOreHolder().getOreAmount(oreName);
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean hasOre(String oreName) {
		if(tile.isConnected()) {
			return tile.getMultiblockController().getInventory().ore.getOreHolder().hasOre(oreName);
		}
		return false;
	}

	@Override
	public void setOreAmount(String oreName, Integer oreLevel) {
		if(tile.isConnected()) {
			tile.getMultiblockController().getInventory().ore.getOreHolder().setOreAmount(oreName, oreLevel);
		}
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		if(tile.isConnected()) {
			return tile.getMultiblockController().getInventory().ore.getOreHolder().serializeNBT();
		}
		return new NBTTagCompound();
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		if(tile.isConnected()) {
			tile.getMultiblockController().getInventory().ore.getOreHolder().deserializeNBT(tag);
		}
	}

}
