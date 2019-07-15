package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.ContainerInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;

public abstract class TileEntityInventory<I extends InventoryBasic> extends TileEntityBase
		implements ITickable, IHasGui, IHasInventory<I> {

	public I inventory;

	@Override
	public I getInventory() {
		if(inventory == null) {
			throw new RuntimeException("Whoops. Machine Inventory NOT SET. Fix this.");
		}
		return inventory;
	}

	@Override
	public void setInventory(I inventory) {
		this.inventory = inventory;
	}

	@Override
	public abstract String getName();

	// Handles sync on world load
	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		SteamAgeRevolution.instance.getLogger().devInfo("getUpdateTag");
		return this.writeToDisk(super.getUpdateTag());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleUpdateTag(NBTTagCompound tag) {
		SteamAgeRevolution.instance.getLogger().devInfo("handleUpdateTag");
		this.readFromDisk(tag);
	}

	@Override
	protected void readFromDisk(NBTTagCompound data) {
		inventory.deserializeNBT(data.getCompoundTag("inventory"));
	}

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		data.setTag("inventory", inventory.serializeNBT());
		return data;
	}

	@Override
	public void update() {
		// TODO
		if(inventory instanceof InventoryRecipe) {
			((InventoryRecipe) inventory).onTick();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiInventory(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerInventory(entityPlayer, this);
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
	public void markMachineDirty() {
		this.markDirty();
	}
}
