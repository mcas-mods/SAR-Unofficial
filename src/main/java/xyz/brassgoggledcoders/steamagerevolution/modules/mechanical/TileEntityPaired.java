package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySlowTick;
import xyz.brassgoggledcoders.boilerplate.utils.PositionUtils;

public class TileEntityPaired extends TileEntitySlowTick {
	private boolean master;
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
		this.master = compound.getBoolean("isMaster");
		if(compound.getLong("pos") != 0)
			this.pos = BlockPos.fromLong(compound.getLong("pos"));
	}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		compound.setBoolean("isMaster", master);
		if(pos != null)
			compound.setLong("pos", pos.toLong());
		return compound;
	}

	public void setMaster() {
		this.master = true;
	}

	public void setSlave() {
		this.master = false;
	}

	public boolean isMaster() {
		return master;
	}

	public static boolean pairBlocks(World worldIn, BlockPos clicked_pos, BlockPos saved_pos) {
		// Check that both ends are actually belts.
		if(worldIn.getTileEntity(clicked_pos) instanceof TileEntityPaired
				&& (worldIn.getChunkFromBlockCoords(saved_pos).isLoaded()
						&& worldIn.getTileEntity(saved_pos) instanceof TileEntityPaired)) {
			TileEntityPaired start = (TileEntityPaired) worldIn.getTileEntity(saved_pos);
			TileEntityPaired end = (TileEntityPaired) worldIn.getTileEntity(clicked_pos);

			// Don't allow pairing if either end is already paired or if you're trying to pair something with
			// itself.
			if(!(end.isTilePaired()) && !(start.isTilePaired()) && saved_pos != clicked_pos) {
				// Ensure pairs are aligned on axes
				if(PositionUtils.arePositionsAlignedOnTwoAxes(clicked_pos, saved_pos)) {
					// Set start's pair, and make it a master.
					start.setPairedTileLoc(clicked_pos);
					start.setMaster();
					// Set end's pair, and make it a slave.
					end.setPairedTileLoc(saved_pos);
					end.setSlave();
					return true;
				}
			}
		}
		return false;
	}
}
