package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankSync extends FluidTank implements INBTSerializable<NBTTagCompound> {

	// Cannot be parented by an InventoryPiece because the handlers are initialised
	// in the InventoryPiece constructor call
	final InventoryBasic containingInventory;
	final String name;

	public FluidTankSync(String name, int capacity, InventoryBasic containingInventory) {
		super(capacity);
		this.containingInventory = containingInventory;
		this.name = name;
		// if(containingInventory.parent instanceof TileEntity) {
		// setTileEntity((TileEntity) containingInventory.parent);
		// }
	}

	@Override
	public void onContentsChanged() {
		containingInventory.onContentsChanged(name, this);
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
