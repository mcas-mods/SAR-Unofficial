package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.LinkedHashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import xyz.brassgoggledcoders.boilerplate.blocks.SideType;
import xyz.brassgoggledcoders.steamagerevolution.CapabilityHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;

public class TileEntityBeltEnd extends TileEntityPaired {

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
								ISpinHandler other_handler = te.getCapability(CapabilityHandler.SPIN_HANDLER_CAPABILITY, null);
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
}
