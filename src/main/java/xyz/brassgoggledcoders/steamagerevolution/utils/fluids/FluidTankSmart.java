package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IOTYPE;

public class FluidTankSmart extends FluidTank {

	final IMachineHasInventory parent;
	IOTYPE type = IOTYPE.UNDEFINED;

	public FluidTankSmart(int capacity, IMachineHasInventory parent2) {
		super(capacity);
		parent = parent2;
		if (parent instanceof TileEntity) {
			setTileEntity((TileEntity) parent);
		}
	}

	public FluidTankSmart(FluidStack fluid, int capacity, IMachineHasInventory parent) {
		super(fluid, capacity);
		this.parent = parent;
		if (parent instanceof TileEntity) {
			setTileEntity((TileEntity) parent);
		}
	}

	public FluidTankSmart setTankType(IOTYPE type) {
		this.type = type;
		return this;
	}

	@Override
	public void onContentsChanged() {
		parent.getInventory().onTankContentsChanged(this, type, parent);
		if (tile != null) {
			tile.markDirty();
		} else {
			RectangularMultiblockControllerBase controller = (RectangularMultiblockControllerBase) parent;
			controller.WORLD.markChunkDirty(controller.getReferenceCoord(), null); // TODO
		}
	}
}
