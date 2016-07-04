package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySlowTick;

public class TileEntityPairedBlock extends TileEntitySlowTick {

	private BlockPos pos;

	// TODO Handling for unpairing when blocks are broken

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
		// TODO Move 'already paired' check to here
		this.pos = pos;
	}

	@Override
	public void readFromNBTCustom(NBTTagCompound compound) {
		if(compound.getLong("pos") != 0)
			this.pos = BlockPos.fromLong(compound.getLong("pos"));
	}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		if(pos != null)
			compound.setLong("pos", pos.toLong());
		return compound;
	}
}
