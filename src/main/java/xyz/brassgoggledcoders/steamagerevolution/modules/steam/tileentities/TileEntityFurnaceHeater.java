package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers.TileEntitySpinConsumer;

public class TileEntityFurnaceHeater extends TileEntitySpinConsumer {

	public TileEntityFurnaceHeater() {
		super(50);
	}

	@Override
	public void tickAtWorkSpeed() {
		if(this.getWorld().getTileEntity(getPos().up()) instanceof TileEntityFurnace) {
			TileEntityFurnace furnace = (TileEntityFurnace) this.getWorld().getTileEntity(getPos().up());
			furnace.getTileData().setInteger("BurnTime", 20);
			furnace.readFromNBT(new NBTTagCompound());
			// furnace.markDirty();
			// furnace.smeltItem();
		}
	}

	@Override
	public void tickAtDangerSpeed() {}
}
