package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.entities.EntityMinecartBase;
import com.teamacronymcoders.base.guisystem.GuiOpener;
import com.teamacronymcoders.base.guisystem.IHasGui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryRecipeMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public abstract class EntityMinecartInventory<I extends InventoryRecipeMachine> extends EntityMinecartBase
		implements IHasGui, IMachineHasInventory<I>, IInventory {

	I inventory;

	public EntityMinecartInventory(World world) {
		super(world);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setTag("inventory", this.inventory.serializeNBT());
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.getInventory().getInputHandler());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		GuiOpener.openEntityGui(SteamAgeRevolution.instance, this, player, world);
		return super.processInitialInteract(player, hand);
	}

	@Override
	public World getMachineWorld() {
		return this.getEntityWorld();
	}

	@Override
	public BlockPos getMachinePos() {
		return this.getPosition();
	}

	@Override
	public SARMachineRecipe getCurrentRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentRecipe(SARMachineRecipe recipe) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCurrentProgress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentMaxTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentTicks(int ticks) {
		// TODO Auto-generated method stub

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
	public int getSizeInventory() {
		return this.getInventory().getInputHandler().getSlots();
	}

	@Override
	public boolean isEmpty() {
		//TODO
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		ItemStack stack = this.getInventory().getInputHandler().getStackInSlot(index);
		return stack;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return this.getInventory().getInputHandler().extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.getInventory().getInputHandler().getStackInSlot(index);
		this.getInventory().getInputHandler().setStackInSlot(index, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.getInventory().getInputHandler().setStackInSlot(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return this.getInventory().getInputHandler().isItemValid(index, stack);
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {}
}
