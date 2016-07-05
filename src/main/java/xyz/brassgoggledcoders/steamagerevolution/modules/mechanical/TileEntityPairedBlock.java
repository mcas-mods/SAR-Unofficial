package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

	public static boolean pairBlocks(World worldIn, BlockPos clicked_pos, BlockPos saved_pos) {
		// Check that both ends are actually belts.
		if(worldIn.getTileEntity(clicked_pos) instanceof TileEntityPairedBlock
				&& (worldIn.getChunkFromBlockCoords(saved_pos).isLoaded()
						&& worldIn.getTileEntity(saved_pos) instanceof TileEntityPairedBlock)) {
			TileEntityPairedBlock start = (TileEntityPairedBlock) worldIn.getTileEntity(saved_pos);
			TileEntityPairedBlock end = (TileEntityPairedBlock) worldIn.getTileEntity(clicked_pos);

			// Don't allow pairing if either end is already paired or if you're trying to pair something with
			// itself.
			if(!(end.isTilePaired()) && !(start.isTilePaired()) && saved_pos != clicked_pos) {
				// Set start's pair
				start.setPairedTileLoc(clicked_pos);
				// start.setMaster();
				// Set end's pair
				end.setPairedTileLoc(saved_pos);
				return true;
				// end.setSlave();
			}
		}
		return false;
	}
}
