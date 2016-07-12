package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import net.minecraft.block.BlockFurnace;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityFurnaceHeater extends TileEntitySpinConsumer {

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		if(this.handler.getSpeed() >= 50) {
			if(this.getWorld().getTileEntity(getPos().up()) instanceof TileEntityFurnace) {
				TileEntityFurnace furnace = (TileEntityFurnace) this.getWorld().getTileEntity(getPos().up());
				BlockFurnace.setState(true, this.getWorld(), getPos().up());
				// TODO Make instant smelting only occur at higher speeds, use normal cooktime otherwise.
				furnace.smeltItem();
			}
		}
	}
}
