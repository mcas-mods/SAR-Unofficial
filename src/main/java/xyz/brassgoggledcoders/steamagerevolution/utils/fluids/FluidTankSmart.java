package xyz.brassgoggledcoders.steamagerevolution.utils.fluids;

import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IMachineHasInventory;

public class FluidTankSmart extends FluidTank implements INBTSerializable<NBTTagCompound> {

	final IMachineHasInventory parent;

	public FluidTankSmart(int capacity, IMachineHasInventory parent2) {
		super(capacity);
		parent = parent2;
		if(parent instanceof TileEntity) {
			setTileEntity((TileEntity) parent);
		}
	}

	public FluidTankSmart(FluidStack fluid, int capacity, IMachineHasInventory parent) {
		super(fluid, capacity);
		this.parent = parent;
		if(parent instanceof TileEntity) {
			setTileEntity((TileEntity) parent);
		}
	}

	@Override
	public void onContentsChanged() {
		// TODO
		parent.getInventory().onTankContentsChanged(this, type, parent);
		if(tile != null) {
			tile.markDirty();
		}
		else {
			RectangularMultiblockControllerBase controller = (RectangularMultiblockControllerBase) parent;
			controller.WORLD.markChunkDirty(controller.getReferenceCoord(), null); // TODO
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.readFromNBT(nbt);
	}
}
