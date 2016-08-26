package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import xyz.brassgoggledcoders.boilerplate.multiblock.MultiblockControllerBase;
import xyz.brassgoggledcoders.boilerplate.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public abstract class TileEntityBasicBoilerPart extends RectangularMultiblockTileEntityBase {

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return BasicBoilerController.class;
	}

	@Override
	public void onMachineActivated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMachineDeactivated() {
		// TODO Auto-generated method stub

	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new BasicBoilerController(getWorld());
	}

	@Override
	public void readFromNBTCustom(NBTTagCompound nbtTagCompound) {
		// TODO Auto-generated method stub

	}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound nbtTagCompound) {
		// TODO Auto-generated method stub
		return null;
	}
}
