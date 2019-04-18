package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.HashMap;
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
			return tile.getMultiblockController().oreLevels.get(oreName);
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean hasOre(String oreName) {
		if(tile.isConnected()) {
			return tile.getMultiblockController().oreLevels.containsKey(oreName);
		}
		return false;
	}

	@Override
	public void setOreLevel(String oreName, Integer oreLevel) {
		if(tile.isConnected()) {
			tile.getMultiblockController().oreLevels.put(oreName, oreLevel);
		}
	}

	@Override
	public HashMap<String, Integer> getMap() {
		if(tile.isConnected()) {
			return tile.getMultiblockController().oreLevels;
		}
		return null;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
	
		if(tile.isConnected()) {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("size", tile.getMultiblockController().oreLevels.size());
		int i = 0;
		for(Entry<String, Integer> entry : tile.getMultiblockController().oreLevels.entrySet()) {
			data.setString("ore" + i, entry.getKey());
			data.setInteger("oreValue" + i, entry.getValue());
			i++;
		}
		return data;
		}
		return new NBTTagCompound();
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
			for(int i = 0; i < tag.getInteger("size"); i++) {
				setOreLevel(tag.getString("ore" + i), tag.getInteger("oreValue" + i));
		}
	}

}
