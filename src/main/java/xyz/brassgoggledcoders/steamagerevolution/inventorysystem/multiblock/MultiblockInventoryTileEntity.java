package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.machines.SARMultiblockTileBase;

public abstract class MultiblockInventoryTileEntity<T extends MultiblockCraftingMachine<? extends InventoryCraftingMachine>>
		extends SARMultiblockTileBase<T> implements IHasInventory<InventoryCraftingMachine> {
	// Handles sync on world load
	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = this.writeToNBT(super.getUpdateTag());
		if(this.isConnected() && this.isMultiblockSaveDelegate()) {
			this.getMultiblockController().writeToDisk(nbt);
		}
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleUpdateTag(NBTTagCompound tag) {
		if(this.isConnected() && this.isMultiblockSaveDelegate()) {
			this.getMultiblockController().onAttachedPartWithMultiblockData(this, tag);
		}
		this.readFromNBT(tag);
	}

	// Delegate IHasInventory/IMachine methods to those of our controller

	@Override
	public InventoryCraftingMachine getInventory() {
		return this.getMultiblockController().getInventory();
	}

	@Override
	public void setInventory(InventoryCraftingMachine inventory) {
		throw new UnsupportedOperationException("Cannot use setInventory on a read-only delegate");
	}

	@Override
	public void markMachineDirty() {
		this.getMultiblockController().markMachineDirty();
	}

	@Override
	public World getMachineWorld() {
		return super.getWorld();
	}

	@Override
	public BlockPos getMachinePos() {
		return super.getPos();
	}

	@Override
	public String getUID() {
		return this.getMultiblockController().getUID();
	}

	@Override
	public ItemStack getCatalyst() {
		return this.getMultiblockController().getCatalyst();
	}
}
