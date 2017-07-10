package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityInventoryBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileEntityTrunk extends TileEntityInventoryBase implements IHasGui, ITickable {

	IInventory inv;
	InvWrapper inventory;
	public float prevLidAngle;
	public float lidAngle;
	public int numPlayersUsing;

	public TileEntityTrunk() {
		super(27);
		inv = new InventoryBasic("container.chest", false, 27);
		inventory = new InvWrapper(inv);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) inventory;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capObject, side);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		NBTTagCompound inv = new NBTTagCompound();
		for(int i = 0; i < inventory.getSlots(); i++) {
			if(!inventory.getStackInSlot(i).isEmpty())
				inv.setTag("slot" + i, inventory.getStackInSlot(i).serializeNBT());
		}
		tag.setTag("inventory", inv);
		return super.writeToDisk(tag);
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		for(int i = 0; i < inventory.getSlots(); i++) {
			inventory.setStackInSlot(i, new ItemStack(tag.getCompoundTag("inventory").getCompoundTag("slot" + i)));
		}
		super.readFromDisk(tag);
	}

	@Override
	public void update() {
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();

		this.prevLidAngle = this.lidAngle;

		if(this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
			double d1 = i + 0.5D;
			double d2 = k + 0.5D;

			this.world.playSound((EntityPlayer) null, d1, j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN,
					SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
		}

		if(this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
			float f2 = this.lidAngle;

			if(this.numPlayersUsing > 0) {
				this.lidAngle += 0.1F;
			}
			else {
				this.lidAngle -= 0.1F;
			}

			if(this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			if(this.lidAngle < 0.5F && f2 >= 0.5F) {
				double d3 = i + 0.5D;
				double d0 = k + 0.5D;

				this.world.playSound((EntityPlayer) null, d3, j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE,
						SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
			}

			if(this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiChest(entityPlayer.inventory, inv);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerChest(entityPlayer.inventory, inv, entityPlayer);
	}
}
