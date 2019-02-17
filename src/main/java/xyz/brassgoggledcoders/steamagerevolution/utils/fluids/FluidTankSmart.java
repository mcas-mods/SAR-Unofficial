package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.TankType;

public class FluidTankSmart extends FluidTank {

	final IHasInventory parent;
	TankType type = TankType.UNDEFINED;

	public FluidTankSmart(int capacity, IHasInventory parent2) {
		super(capacity);
		parent = parent2;
		if(parent instanceof TileEntity) {
			setTileEntity((TileEntity) parent);
		}
	}

	public FluidTankSmart(FluidStack fluid, int capacity, IHasInventory parent) {
		super(fluid, capacity);
		this.parent = parent;
		if(parent instanceof TileEntity) {
			setTileEntity((TileEntity) parent);
		}
	}

	public FluidTankSmart setTankType(TankType type) {
		this.type = type;
		return this;
	}

	@Override
	public void onContentsChanged() {
		parent.getInventory().onTankContentsChanged(this, type, parent);
		if(tile != null) {
			tile.markDirty();
		}
		else {
			RectangularMultiblockControllerBase controller = (RectangularMultiblockControllerBase) parent;
			controller.WORLD.markChunkDirty(controller.getReferenceCoord(), null); // TODO
		}
	}
}
