package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankSmart extends FluidTank {

	final int id;
	final ISmartTankCallback parent;

	@Deprecated
	public FluidTankSmart(int capacity, ISmartTankCallback parent) {
		super(capacity);
		if(parent instanceof TileEntity) {
			setTileEntity((TileEntity) parent);
		}
		this.parent = parent;
		id = -1;
	}

	public FluidTankSmart(int capacity, ISmartTankCallback parent2, int id) {
		super(capacity);
		this.parent = parent2;
		if(parent instanceof TileEntity) {
			setTileEntity((TileEntity) parent);
		}
		this.id = id;
	}

	@Deprecated
	public FluidTankSmart(FluidStack fluid, int capacity, ISmartTankCallback parent) {
		super(fluid, capacity);
		this.parent = parent;
		if(parent instanceof TileEntity) {
			setTileEntity((TileEntity) parent);
		}
		id = -1;
	}

	public int getId() {
		return id;
	}

	@Override
	public void onContentsChanged() {
		if(tile != null) {
			((ISmartTankCallback) tile).onTankContentsChanged(this);
			tile.markDirty();
		}
		else {
			((ISmartTankCallback) parent).onTankContentsChanged(this);
			RectangularMultiblockControllerBase controller = (RectangularMultiblockControllerBase) parent;
			controller.WORLD.markChunkDirty(controller.getReferenceCoord(), null); // TODO
		}
	}
}
