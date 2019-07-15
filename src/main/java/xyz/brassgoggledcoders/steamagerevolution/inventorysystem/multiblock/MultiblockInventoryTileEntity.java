package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockRecipe;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileBase;

public abstract class MultiblockInventoryTileEntity<T extends MultiblockRecipe<? extends InventoryRecipe>>
		extends SARMultiblockTileBase<T> implements IHasInventory<InventoryRecipe> {
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

	@Override
	public String getName() {
		return this.getMultiblockController().getName();
	}

	@Override
	public InventoryRecipe getInventory() {
		return this.getMultiblockController().getInventory();
	}

	@Override
	public void setInventory(InventoryRecipe inventory) {
		// TODO
		// return this.getMultiblockController().setInventory(inventory);
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
}
