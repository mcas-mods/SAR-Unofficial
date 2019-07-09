package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.ContainerInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.recipes.RecipeMachineHelper;
import xyz.brassgoggledcoders.steamagerevolution.recipes.SARMachineRecipe;

public abstract class RecipeTileEntity extends TileEntityBase
		implements ITickable, IHasGui, IRecipeMachine<InventoryRecipe> {

	public int currentTicks = 0;
	public InventoryRecipe inventory;
	SARMachineRecipe currentRecipe;

	@Override
	public InventoryRecipe getInventory() {
		if(inventory == null) {
			throw new RuntimeException("Whoops. Machine Inventory NOT SET. Fix this.");
		}
		return inventory;
	}

	@Override
	public void setInventory(InventoryRecipe inventory) {
		this.inventory = inventory;
	}

	@Override
	public abstract String getName();

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("inventory", inventory.serializeNBT());
		return new SPacketUpdateTileEntity(pos, 3, nbt);
	}

	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
		nbt.setTag("inventory", inventory.serializeNBT());
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		inventory.deserializeNBT(pkt.getNbtCompound().getCompoundTag("inventory"));
	}

	@Override
	protected void readFromDisk(NBTTagCompound data) {
		currentTicks = data.getInteger("progress");
		inventory.deserializeNBT(data.getCompoundTag("inventory"));
	}

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		data.setInteger("progress", currentTicks);
		data.setTag("inventory", inventory.serializeNBT());
		return data;
	}

	@Override
	public void update() {
		onTick();
		if(canRun()) {
			onActiveTick();
			currentTicks++;
			if(canFinish()) {
				onFinish();
			}
		}
	}

	protected void onTick() {
		// NO-OP
	}

	protected void onActiveTick() {
		// NO-OP
	}

	protected void onFinish() {
		RecipeMachineHelper.onFinish(currentRecipe, inventory);
		currentTicks = 0;
		currentRecipe = null; // TODO Only null when inputs hit zero
	}

	protected boolean canFinish() {
		return RecipeMachineHelper.canFinish(currentTicks, currentRecipe, inventory);
	}

	// TODO: Cache this check to occur say every second instead of every tick?
	// Potentially dangerous
	protected boolean canRun() {
		return RecipeMachineHelper.canRun(world, pos, this, getName().toLowerCase()/* .replace(' ', '_')TODO */,
				currentRecipe, inventory);
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
	public SARMachineRecipe getCurrentRecipe() {
		return currentRecipe;
	}

	@Override
	public void setCurrentRecipe(SARMachineRecipe recipe) {
		currentRecipe = recipe;
	}

	@Override
	public int getCurrentProgress() {
		return currentTicks;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getCurrentMaxTicks() {
		return 0; // TODO
	}

	@Override
	public void setCurrentTicks(int ticks) {
		currentTicks = ticks;
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
