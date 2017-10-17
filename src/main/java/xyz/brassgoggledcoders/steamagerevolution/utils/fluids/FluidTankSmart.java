package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankSmart extends FluidTank {

	int id;
	MultiblockControllerBase parent = null;

	public FluidTankSmart(int capacity, TileEntity parent) {
		super(capacity);
		setTileEntity(parent);
	}

	public FluidTankSmart(int capacity, MultiblockControllerBase parent) {
		this(capacity, parent, 0);
	}

	public FluidTankSmart(int capacity, MultiblockControllerBase parent, int id) {
		super(capacity);
		this.parent = parent;
		this.id = id;
	}

	public FluidTankSmart(FluidStack fluid, int capacity, MultiblockControllerBase parent) {
		super(fluid, capacity);
		this.parent = parent;
	}

	public int getId() {
		return id;
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
