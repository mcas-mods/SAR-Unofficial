package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.ContainerInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.recipes.RecipeMachineHelper;
import xyz.brassgoggledcoders.steamagerevolution.recipes.SARMachineRecipe;

public abstract class SARMultiblockInventory<I extends InventoryRecipeMachine> extends SARMultiblockBase
		implements IMachineHasInventory<I> {

	public I inventory;
	@SideOnly(Side.CLIENT)
	public int currentMaxTicks;
	protected int currentTicks = 0;
	protected SARMachineRecipe currentRecipe;

	protected SARMultiblockInventory(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {
		onTick();
		if(canRun()) {
			if(currentTicks <= currentRecipe.getTicksPerOperation()) { // TODO
				currentTicks++;
			}
			onActiveTick();
			if(canFinish()) {
				onFinish();
				currentTicks = 0;
				currentRecipe = null;
			}
			return true; // TODO
		}
		return false;
	}

	protected void onTick() {
		// NO-OP
	}

	protected void onActiveTick() {
		// NO-OP
	}

	protected void onFinish() {
		RecipeMachineHelper.onFinish(currentRecipe, inventory);
		markReferenceCoordForUpdate();
	}

	protected boolean canFinish() {
		return RecipeMachineHelper.canFinish(currentTicks, currentRecipe, inventory);
	}

	protected boolean canRun() {
		if(currentRecipe != null) {
			// TODO Send this (much!) less often!
			markReferenceCoordForUpdate();
		}
		return RecipeMachineHelper.canRun(WORLD, getReferenceCoord(), this,
				getName().toLowerCase()/* .replace(' ', '_')TODO */, currentRecipe, inventory);
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		currentTicks = data.getInteger("progress");
		inventory.deserializeNBT(data.getCompoundTag("inventory"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setInteger("progress", currentTicks);
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

	@Override
	public SARMachineRecipe getCurrentRecipe() {
		return currentRecipe;
	}

	@Override
	public void setCurrentRecipe(SARMachineRecipe recipe) {
		if(recipe == null) {
			this.setCurrentTicks(0);
		}
		currentRecipe = recipe;
	}

	@Override
	public int getCurrentProgress() {
		return currentTicks;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getCurrentMaxTicks() {
		return currentMaxTicks;
	}

	@Override
	public void setCurrentTicks(int ticks) {
		currentTicks = ticks;
	}
}
