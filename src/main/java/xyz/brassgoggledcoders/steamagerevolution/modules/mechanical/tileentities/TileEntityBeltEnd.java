package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.Iterator;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.SideType;
import xyz.brassgoggledcoders.steamagerevolution.CapabilityHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltDummy;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltEnd;

public class TileEntityBeltEnd extends TileEntitySpinMachine {

	private boolean master;
	private BlockPos paired_pos;

	@Nullable
	public TileEntityBeltEnd getPairedTile() {
		if(this.paired_pos == null)
			return null;

		if(this.getWorld().getChunkFromBlockCoords(this.paired_pos).isLoaded()
				&& this.getWorld().getTileEntity(this.paired_pos) instanceof TileEntityBeltEnd)
			return (TileEntityBeltEnd) this.getWorld().getTileEntity(this.paired_pos);
		else
			return null;
	}

	public boolean isTilePaired() {
		return this.paired_pos != null;
	}

	public void setPairedTileLoc(BlockPos pos) {
		// TODO Move 'already paired' check to here
		this.paired_pos = pos;
		// SteamAgeRevolution.instance.getLogger().devInfo("Paired location set to: " + this.paired_pos.toString());
	}

	@Override
	public void readFromDisk(NBTTagCompound compound) {
		this.master = compound.getBoolean("master");
		if(compound.getLong("pos") != 0) {
			SteamAgeRevolution.instance.getLogger().devInfo("Reading pair position");
			this.paired_pos = BlockPos.fromLong(compound.getLong("pos"));
		}
		super.readFromDisk(compound);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound compound) {
		compound.setBoolean("master", this.master);
		if(this.paired_pos != null) {
			compound.setLong("pos", this.paired_pos.toLong());
			SteamAgeRevolution.instance.getLogger().devInfo("Writing pair position");
		}
		return super.writeToDisk(compound);
	}

	public void setMaster() {
		SteamAgeRevolution.instance.getLogger().devInfo("Master set");
		this.master = true;
	}

	public void setSlave() {
		this.master = false;
	}

	public boolean isMaster() {
		return this.master;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void updateTile() {
		if(this.isTilePaired()) {
			// SteamAgeRevolution.instance.getLogger().devInfo("Paired (1)");
			if(this.getPairedTile() != null) {
				// SteamAgeRevolution.instance.getLogger().devInfo("Paired (2)");
				if(this.isMaster()) {
					// SteamAgeRevolution.instance.getLogger().devInfo("Master");
					this.getPairedTile().getCapability(CapabilityHandler.SPIN_HANDLER_CAPABILITY, null)
							.setSpeed(Math.round(this.handler.getSpeed()
									* ((BlockBeltEnd) getWorld().getBlockState(getPos()).getBlock()).getSlipFactor()));
					boolean flag = false;
					if(this.handler.getSpeed() > 0) {
						flag = true;
					}
					BlockPos from = this.getPos();
					BlockPos to = this.getPairedTile().getPos();
					Iterator<BlockPos> positions = BlockPos.getAllInBox(from, to).iterator();
					while(positions.hasNext()) {
						BlockPos pos = positions.next();

						if(pos.equals(from) || pos.equals(to)) {
							continue;
						}
						// TODO This resets facing
						getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos),
								getWorld().getBlockState(pos).withProperty(BlockBeltDummy.SPINNING, flag), 3);
					}
				}
				else {
					// Logic mostly copied from SpinUtils
					for(EnumFacing element : EnumFacing.VALUES) {
						if(this.getSideValue(element.ordinal()) == SideType.OUTPUT) {
							BlockPos off = this.pos.offset(element);

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
	}
}
