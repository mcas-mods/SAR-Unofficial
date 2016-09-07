package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityFurnaceHeater extends TileEntitySpinConsumer {

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		if(this.handler.getSpeed() >= 50) {
			if(this.getWorld().getTileEntity(getPos().up()) instanceof TileEntityFurnace) {
				TileEntityFurnace furnace = (TileEntityFurnace) this.getWorld().getTileEntity(getPos().up());
				furnace.getTileData().setInteger("BurnTime", 20);
				furnace.readFromNBT(new NBTTagCompound());
				// furnace.markDirty();
				// furnace.smeltItem();
			}
		}
	}
}
