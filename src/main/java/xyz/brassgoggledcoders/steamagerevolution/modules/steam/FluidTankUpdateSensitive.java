package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankUpdateSensitive extends FluidTank {
	public FluidTankUpdateSensitive(FluidStack passIn, int capacity, TileEntity parent) {
		super(passIn, capacity);
		setTileEntity(parent);
	}

	public FluidTankUpdateSensitive(int capacity, TileEntity parent) {
		this(null, capacity, parent);
	}

	@Override
	public void onContentsChanged() {
		if(tile != null) {
			((ITankCallback) this.tile).onTankContentsChanged(this);
		}
	}
}
