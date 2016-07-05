package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityOneWayPair extends TileEntityPairedBlock {
	private boolean master;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.master = compound.getBoolean("master");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("master", master);
		return super.writeToNBT(compound);
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
		if(worldIn.getTileEntity(clicked_pos) instanceof TileEntityOneWayPair
				&& (worldIn.getChunkFromBlockCoords(saved_pos).isLoaded()
						&& worldIn.getTileEntity(saved_pos) instanceof TileEntityOneWayPair)) {
			TileEntityOneWayPair start = (TileEntityOneWayPair) worldIn.getTileEntity(saved_pos);
			TileEntityOneWayPair end = (TileEntityOneWayPair) worldIn.getTileEntity(clicked_pos);

			// Don't allow pairing if either end is already paired or if you're trying to pair something with
			// itself.
			if(!(end.isTilePaired()) && !(start.isTilePaired()) && saved_pos != clicked_pos) {
				// Set start's pair
				start.setPairedTileLoc(clicked_pos);
				start.setMaster();
				// Set end's pair
				end.setPairedTileLoc(saved_pos);
				end.setSlave();
				return true;
			}
		}
		return false;
	}
}
