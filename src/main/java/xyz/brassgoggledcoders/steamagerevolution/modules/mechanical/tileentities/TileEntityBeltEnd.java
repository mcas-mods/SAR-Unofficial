package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.boilerplate.blocks.SideType;
import xyz.brassgoggledcoders.boilerplate.utils.PositionUtils;
import xyz.brassgoggledcoders.steamagerevolution.CapabilityHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.ModuleMechanical;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltEnd;

public class TileEntityBeltEnd extends TileEntitySpinMachine {

	private float slipFactor;

	public TileEntityBeltEnd(float slipFactor) {
		super();
		this.slipFactor = slipFactor;
	}

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		if(this.isTilePaired() && this.getPairedTile() != null) {
			if(this.isMaster()) {
				this.getPairedTile().getCapability(CapabilityHandler.SPIN_HANDLER_CAPABILITY, null)
						.setSpeed(Math.round(handler.getSpeed() * slipFactor));
			}
			else {
				// Logic mostly copied from SpinUtils
				for(EnumFacing element : EnumFacing.VALUES) {
					if(this.getSideValue(element.ordinal()) == SideType.OUTPUT) {
						BlockPos off = pos.offset(element);

						if(this.getWorld().getTileEntity(off) != null) {
							TileEntity te = this.getWorld().getTileEntity(off);

							if(te.hasCapability(CapabilityHandler.SPIN_HANDLER_CAPABILITY, null)) {
								ISpinHandler other_handler =
										te.getCapability(CapabilityHandler.SPIN_HANDLER_CAPABILITY, null);
								if(this.handler.getSpeed() > other_handler.getSpeed()) {
									other_handler.setSpeed(this.handler.getSpeed());
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public LinkedHashMap<String, String> getDebugStrings(LinkedHashMap<String, String> debugStrings) {
		if(this.getPairedTile() != null) {
			debugStrings.put("paired_loc", this.getPairedTile().getPos().toString());
		}
		debugStrings.put("slip_factor", "" + this.slipFactor);
		return super.getDebugStrings(debugStrings);
	}

	private boolean master;
	private BlockPos paired_pos;

	@Nullable
	public TileEntityBeltEnd getPairedTile() {
		if(paired_pos == null)
			return null;

		if(this.getWorld().getChunkFromBlockCoords(paired_pos).isLoaded()
				&& this.getWorld().getTileEntity(paired_pos) instanceof TileEntityBeltEnd)
			return (TileEntityBeltEnd) this.getWorld().getTileEntity(paired_pos);
		else
			return null;
	}

	public static void unpair(TileEntityBeltEnd tile) {
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
		FMLLog.warning("" + tile.getPairedTile().getPos(), "");
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
		if(worldIn.getTileEntity(clicked_pos) instanceof TileEntityBeltEnd
				&& worldIn.getChunkFromBlockCoords(saved_pos).isLoaded()
				&& worldIn.getTileEntity(saved_pos) instanceof TileEntityBeltEnd) {
			SteamAgeRevolution.instance.getLogger().devInfo("Second paircheck passed (instanceof)");
			TileEntityBeltEnd start = (TileEntityBeltEnd) worldIn.getTileEntity(saved_pos);
			TileEntityBeltEnd end = (TileEntityBeltEnd) worldIn.getTileEntity(clicked_pos);

			// Don't allow pairing if either end is already paired or if you're trying to pair something with
			// itself.
			if(!end.isTilePaired() && !start.isTilePaired() && saved_pos != clicked_pos) {
				SteamAgeRevolution.instance.getLogger().devInfo("Third paircheck passed (not already paired)");
				// Ensure pairs are aligned on axes
				if(PositionUtils.arePositionsAlignedOnTwoAxes(clicked_pos, saved_pos)) {
					SteamAgeRevolution.instance.getLogger().devInfo("Fourth paircheck passed (alignment)");
					if(PositionUtils.isLOSClear(worldIn, saved_pos, clicked_pos)) {
						SteamAgeRevolution.instance.getLogger().devInfo("Fifth paircheck passed (clear LOS)");
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
							// Skip over actual ends themselves
							if(pos.equals(clicked_pos) || pos.equals(saved_pos))
								continue;
							// TODO States. This should not be here.
							worldIn.setBlockState(pos, ModuleMechanical.belt_dummy.getDefaultState());
						}
						// Set facings of ends
						worldIn.setBlockState(start.getPos(), worldIn.getBlockState(start.getPos()).withProperty(
								BlockBeltEnd.FACING, PositionUtils.getFacingFromPositions(clicked_pos, saved_pos)));
						worldIn.setBlockState(end.getPos(), worldIn.getBlockState(end.getPos()).withProperty(
								BlockBeltEnd.FACING, PositionUtils.getFacingFromPositions(saved_pos, clicked_pos)));
						return true;
					}

				}
			}
		}
		return false;
	}
}
