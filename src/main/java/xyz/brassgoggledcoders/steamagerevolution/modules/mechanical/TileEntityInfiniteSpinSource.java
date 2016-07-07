package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public class TileEntityInfiniteSpinSource extends TileEntitySpinMachine {

	private ISpinHandler handler = new SpinHandler(1000000000, 999);

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		this.handler.fill(this.handler.getMaxSpin());

		for(int i = 0; i < EnumFacing.VALUES.length; i++) {
			if(this.getWorld().getTileEntity(getPos().offset(EnumFacing.VALUES[i]))
					.hasCapability(SARAPI.SPIN_HANDLER_CAPABILITY, null)) {
				this.getWorld().getTileEntity(getPos().offset(EnumFacing.VALUES[i]))
						.getCapability(SARAPI.SPIN_HANDLER_CAPABILITY, null).fill();
			}
		}
	}

	@Override
	public void readFromNBTCustom(NBTTagCompound nbtTagCompound) {}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		return compound;
	}
}
