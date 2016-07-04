package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TilePairedBlock extends TileEntity {

	private BlockPos pos;

	@Nullable
	public TileEntity getPairedTile() {
		if(this.getWorld().getChunkFromBlockCoords(pos).isLoaded())
			return this.getWorld().getTileEntity(pos);
		else
			return null;
	}

	public boolean isTilePaired() {
		return (pos != null);
	}

	public void setPairedTileLoc(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.pos = BlockPos.fromLong(compound.getLong("pos"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("pos", pos.toLong());
		return super.writeToNBT(compound);
	}
}
