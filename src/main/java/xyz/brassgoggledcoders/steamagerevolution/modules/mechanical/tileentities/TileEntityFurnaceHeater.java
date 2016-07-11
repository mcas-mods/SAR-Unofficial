package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.ArrayList;

import net.minecraft.block.BlockFurnace;
import net.minecraft.tileentity.TileEntityFurnace;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.SpinUtils;

public class TileEntityFurnaceHeater extends TileEntitySpinConsumer {

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		ArrayList<ISpinHandler> handlers = SpinUtils.getHandlersNearby(this.getWorld(), this.getPos());

		for(ISpinHandler handler : handlers) {
			// if(handler.getSpeed() > this.handler.getSpeed())
			this.handler.setSpeed(handler.getSpeed());
		}

		if(this.handler.getSpeed() >= 100) {
			if(this.getWorld().getTileEntity(getPos().up()) instanceof TileEntityFurnace) {
				TileEntityFurnace furnace = (TileEntityFurnace) this.getWorld().getTileEntity(getPos().up());
				BlockFurnace.setState(true, this.getWorld(), getPos().up());
				// TODO Make instant smelting only occur at higher speeds, use normal cooktime otherwise.
				furnace.smeltItem();
			}
		}
	}
}
