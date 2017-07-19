package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankSmart extends FluidTank {

	MultiblockControllerBase parent = null;

	public FluidTankSmart(int capacity, TileEntity parent) {
		super(capacity);
		setTileEntity(parent);
	}

	public FluidTankSmart(int capacity, MultiblockControllerBase parent) {
		super(capacity);
		this.parent = parent;
	}

	@Override
	public void onContentsChanged() {
		if(tile != null) {
			((ISmartTankCallback) this.tile).onTankContentsChanged(this);
		}
		else if(parent != null) {
			((ISmartTankCallback) this.parent).onTankContentsChanged(this);
		}
	}
}
