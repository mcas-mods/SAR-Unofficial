package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.Iterator;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.utils.PositionUtils;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.ModuleMechanical;

// TODO This isn't cleanly split from TileEntityBeltEnd
public abstract class TileEntityPaired extends TileEntitySpinMachine {

	private boolean master;
	private BlockPos paired_pos;

	@Nullable
	public TileEntityPaired getPairedTile() {
		if(paired_pos == null)
			return null;

		if(this.getWorld().getChunkFromBlockCoords(paired_pos).isLoaded()
				&& this.getWorld().getTileEntity(paired_pos) instanceof TileEntityPaired)
			return (TileEntityPaired) this.getWorld().getTileEntity(paired_pos);
		else
			return null;
	}

	public static void unpair(TileEntityPaired tile) {
		// Remove the dummy blocks.
		Iterator<BlockPos> positions = BlockPos.getAllInBox(tile.getPos(), tile.getPairedTile().getPos()).iterator();
		while(positions.hasNext()) {
			BlockPos pos = positions.next();

			if(pos.equals(tile.getPos()) || pos.equals(tile.getPairedTile().getPos()))
				continue;

			tile.getWorld().setBlockToAir(pos);
		}
		// Unpair tiles
		tile.getPairedTile().setPairedTileLoc(null);
		tile.getPairedTile().master = false;
		tile.setPairedTileLoc(null);
		tile.master = false;
	}

	public boolean isTilePaired() {
		return paired_pos != null;
	}

	public void setPairedTileLoc(BlockPos pos) {
		// TODO Move 'already paired' check to here
		this.paired_pos = pos;
	}

	@Override
	public void readFromNBTCustom(NBTTagCompound compound) {
		this.master = compound.getBoolean("isMaster");
		if(compound.getLong("pos") != 0)
			this.paired_pos = BlockPos.fromLong(compound.getLong("pos"));
	}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		compound.setBoolean("isMaster", master);
		if(paired_pos != null)
			compound.setLong("pos", paired_pos.toLong());
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
		// Check that both ends are actually pairable.
		if(worldIn.getTileEntity(clicked_pos) instanceof TileEntityPaired
				&& worldIn.getChunkFromBlockCoords(saved_pos).isLoaded()
				&& worldIn.getTileEntity(saved_pos) instanceof TileEntityPaired) {
			SteamAgeRevolution.instance.getLogger().devInfo("First paircheck passed (instanceof)");
			TileEntityPaired start = (TileEntityPaired) worldIn.getTileEntity(saved_pos);
			TileEntityPaired end = (TileEntityPaired) worldIn.getTileEntity(clicked_pos);

			// Don't allow pairing if either end is already paired or if you're trying to pair something with
			// itself.
			if(!end.isTilePaired() && !start.isTilePaired() && saved_pos != clicked_pos) {
				SteamAgeRevolution.instance.getLogger().devInfo("Second paircheck passed (not already paired)");
				// Ensure pairs are aligned on axes
				if(PositionUtils.arePositionsAlignedOnTwoAxes(clicked_pos, saved_pos)) {
					SteamAgeRevolution.instance.getLogger().devInfo("Third paircheck passed (alignment)");
					if(PositionUtils.getDistanceBetweenPositions(clicked_pos, saved_pos) <= 6) {
						SteamAgeRevolution.instance.getLogger().devInfo("Fifth paircheck passed (distance)");
						if(PositionUtils.isLOSClear(worldIn, saved_pos, clicked_pos)) {
							SteamAgeRevolution.instance.getLogger().devInfo("Fifth paircheck passed (clear LOS)");
							// TODO Distance check.
							// Set start's pair, and make it a master.
							start.setPairedTileLoc(clicked_pos);
							start.setMaster();
							// Set end's pair, and make it a slave.
							end.setPairedTileLoc(saved_pos);
							end.setSlave();
							// Add the dummy blocks.
							Iterator<BlockPos> positions = BlockPos.getAllInBox(clicked_pos, saved_pos).iterator();
							while(positions.hasNext()) {
								BlockPos pos = positions.next();
								if(pos.equals(clicked_pos) || pos.equals(saved_pos))
									continue;
								// TODO States. This should not be here.
								worldIn.setBlockState(pos, ModuleMechanical.belt_dummy.getDefaultState());
							}
							return true;
						}

					}
				}
			}
		}
		return false;
	}
}
