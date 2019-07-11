package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankSmart extends FluidTank implements INBTSerializable<NBTTagCompound> {

	// Cannot be parented by an InventoryPiece because the handlers are initialised
	// in the InventoryPiece constructor call
	final InventoryBasic containingInventory;

	public FluidTankSmart(int capacity, InventoryBasic containingInventory) {
		super(capacity);
		this.containingInventory = containingInventory;
		// TODO
		// if(containingInventory.parent instanceof TileEntity) {
		// setTileEntity((TileEntity) containingInventory.parent);
		// }
	}

	@Override
	public void onContentsChanged() {
		// TODO
		containingInventory.onContentsChanged(this);
		if(tile != null) {
			tile.markDirty();
		}
		else {
			// TODO
			RectangularMultiblockControllerBase controller = (RectangularMultiblockControllerBase) containingInventory.parent;
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
