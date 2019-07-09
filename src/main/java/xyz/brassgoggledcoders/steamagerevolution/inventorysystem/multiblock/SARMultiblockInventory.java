package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.ContainerInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockBase;

public abstract class SARMultiblockInventory<I extends InventoryBasic> extends SARMultiblockBase
		implements IHasInventory<I> {

	protected I inventory;

	protected SARMultiblockInventory(World world) {
		super(world);
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		inventory.deserializeNBT(data.getCompoundTag("inventory"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("inventory", inventory.serializeNBT());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiInventory(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerInventory(entityPlayer, this);
	}

	@Override
	public I getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(I inventory) {
		this.inventory = inventory;
	}
}
